package com.jxareas.techhub.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.jxareas.techhub.R
import com.jxareas.techhub.adapter.CourseAdapterListener
import com.jxareas.techhub.adapter.CourseListAdapter
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.FragmentFavoriteCoursesBinding
import com.jxareas.techhub.utils.animation.SpringAddItemAnimator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteCoursesFragment : Fragment(), CourseAdapterListener {

    private var _binding: FragmentFavoriteCoursesBinding? = null
    private val binding: FragmentFavoriteCoursesBinding
        get() = _binding!!

    private val viewModel: FavoriteCoursesViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        viewModel.getAllFavoriteCourses()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteCoursesBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }


    private fun setupRecyclerView() = binding.recyclerViewFavoriteCourses.run {
        itemAnimator = SpringAddItemAnimator()
        adapter = CourseListAdapter(this@FavoriteCoursesFragment)
    }

    private fun setupObservers() {
        viewModel.favorites.observe(viewLifecycleOwner) { cachedCourses ->
            cachedCourses?.let { favoriteCourses ->
                (binding.recyclerViewFavoriteCourses.adapter as CourseListAdapter).submitList(favoriteCourses)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onArtworkClicked(layout: ViewGroup, course: CachedCourse) {
        val extras = FragmentNavigatorExtras(
            layout to getString(R.string.course_detail_course_image_transition)
        )
        val direction = FavoriteCoursesFragmentDirections.actionFavoriteToDetails(course.courseId)
        findNavController().navigate(direction, extras)
    }

}