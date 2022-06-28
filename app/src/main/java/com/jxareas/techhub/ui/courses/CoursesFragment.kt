package com.jxareas.techhub.ui.courses

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
import com.jxareas.techhub.adapter.CourseCardAdapter
import com.jxareas.techhub.adapter.listeners.CourseAdapterListener
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.FragmentCoursesBinding
import com.jxareas.techhub.utils.animation.SpringAddItemAnimator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoursesFragment : Fragment(), CourseAdapterListener {

    private var _binding: FragmentCoursesBinding? = null
    private val binding: FragmentCoursesBinding
        get() = _binding!!

    private val courseCardAdapter = CourseCardAdapter(this)
    private val viewModel: CoursesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupObservers()
        setupListeners()
        return binding.root
    }

    private fun setupListeners() = binding.searchViewExploreCourses.run {
        setOnQueryTextFocusChangeListener { searchView, hasFocus ->
            if (hasFocus)
                searchView.clearFocus().also { navigateToExpandedSearch(searchView) }
        }

        setOnClickListener { searchView -> navigateToExpandedSearch(searchView) }
    }

    private fun navigateToExpandedSearch(searchView: View) {
        val transitionName = getString(R.string.search_view_transition)
        val extras = FragmentNavigatorExtras(
            searchView to transitionName
        )
        val direction = CoursesFragmentDirections.actionCoursesToExpandedSearch()
        findNavController().navigate(direction, extras)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun setupObservers() {
        viewModel.courses.observe(viewLifecycleOwner) { listOfCourses ->
            listOfCourses?.let { newCourses -> courseCardAdapter.submitList(newCourses) }
        }
    }

    private fun setupRecyclerView() = binding.recyclerViewCourses.run {
        itemAnimator = SpringAddItemAnimator()
        adapter = courseCardAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCourseClicked(viewGroup: ViewGroup, course: CachedCourse) {
        val extras =
            FragmentNavigatorExtras(viewGroup to getString(R.string.course_detail_course_image_transition))
        val direction =
            CoursesFragmentDirections.coursesToDetailFragment(course.courseId)
        findNavController().navigate(direction, extras)
    }

}
