package com.jxareas.techhub.ui.search

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialContainerTransform
import com.jxareas.techhub.R
import com.jxareas.techhub.adapter.CourseListAdapter
import com.jxareas.techhub.adapter.listeners.CourseAdapterListener
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.FragmentSearchResultsBinding
import com.jxareas.techhub.utils.animation.SpringAddItemAnimator
import com.jxareas.techhub.utils.extensions.getLong
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultsFragment : Fragment(), CourseAdapterListener {

    private var _binding: FragmentSearchResultsBinding? = null
    private val binding: FragmentSearchResultsBinding
        get() = _binding!!

    private var coursesAdapter = CourseListAdapter(this@SearchResultsFragment)
    private val viewModel: SearchResultsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment_main
            scrimColor = Color.TRANSPARENT
            duration = resources.getLong(R.integer.material_motion_duration_medium_2)
            interpolator = FastOutSlowInInterpolator()
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchResultsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun setupRecyclerView() = binding.recyclerViewRecentCourses.run {
        itemAnimator = SpringAddItemAnimator()
        adapter = coursesAdapter
    }

    private fun setupObservers() {
        viewModel.courses.observe(viewLifecycleOwner) { listOfCourses ->
            listOfCourses?.let { newCourses -> coursesAdapter.submitList(newCourses) }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCourseClicked(viewGroup: ViewGroup, course: CachedCourse) {
        val transitionName = getString(R.string.course_detail_course_image_transition)
        val extras =
            FragmentNavigatorExtras(viewGroup to transitionName)
        val direction =
            SearchResultsFragmentDirections.actionSearchResultsToDetails(course.courseId)
        findNavController().navigate(direction, extras)
    }

}
