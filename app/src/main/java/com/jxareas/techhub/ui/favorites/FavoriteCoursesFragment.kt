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
import androidx.recyclerview.widget.ItemTouchHelper
import com.jxareas.techhub.R
import com.jxareas.techhub.adapter.FavoriteListAdapter
import com.jxareas.techhub.adapter.callbacks.ItemTouchHelperCallback
import com.jxareas.techhub.adapter.listeners.FavoriteAdapterListener
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.FragmentFavoriteCoursesBinding
import com.jxareas.techhub.utils.animation.SpringAddItemAnimator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteCoursesFragment : Fragment(), FavoriteAdapterListener {

    private var _binding: FragmentFavoriteCoursesBinding? = null
    private val binding: FragmentFavoriteCoursesBinding
        get() = _binding!!

    private val favoritesAdapter = FavoriteListAdapter(this)
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
        adapter = favoritesAdapter
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(favoritesAdapter))
        itemTouchHelper.attachToRecyclerView(this)
    }

    private fun setupObservers() {
        viewModel.favorites.observe(viewLifecycleOwner) { listOfCourses ->
            listOfCourses?.let { favoriteCourses -> favoritesAdapter.submitList(favoriteCourses) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFavoriteCourseSwiped(courseId: Int) {
        viewModel.onFavoriteRemoved(courseId)
    }


    override fun onCourseClicked(viewGroup: ViewGroup, course: CachedCourse) {
        val extras =
            FragmentNavigatorExtras(viewGroup to getString(R.string.course_detail_course_image_transition))
        val directions =
            FavoriteCoursesFragmentDirections.actionFavoriteToDetails(course.courseId)
        findNavController().navigate(directions, extras)
    }

}
