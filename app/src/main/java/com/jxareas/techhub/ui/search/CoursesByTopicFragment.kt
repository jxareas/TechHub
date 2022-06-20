package com.jxareas.techhub.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jxareas.techhub.R
import com.jxareas.techhub.adapter.CourseListAdapter
import com.jxareas.techhub.adapter.listeners.CourseAdapterListener
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.FragmentCoursesByTopicBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoursesByTopicFragment : Fragment(), CourseAdapterListener {

    private var _binding: FragmentCoursesByTopicBinding? = null
    private val binding: FragmentCoursesByTopicBinding
        get() = _binding!!

    private val args: CoursesByTopicFragmentArgs by navArgs()
    private val viewModel : CoursesByTopicViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursesByTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        binding.toolbar.title = args.topic
        setupListeners()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() = binding.recyclerViewCourses.run {
        adapter = CourseListAdapter(this@CoursesByTopicFragment)
    }

    private fun setupObservers() {
        viewModel.courses.observe(viewLifecycleOwner) { listOfCourses ->
            listOfCourses?.let {
                (binding.recyclerViewCourses.adapter as CourseListAdapter).submitList(it)
            }

        }
    }

    private fun setupListeners() {
       binding.toolbar.setNavigationOnClickListener {
           findNavController().navigateUp()
       }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCourseClicked(layout: ViewGroup, course: CachedCourse) {
        val extras = FragmentNavigatorExtras(
            layout to getString(R.string.course_detail_course_image_transition)
        )
        val direction = CoursesByTopicFragmentDirections.coursesByTopicToDetail(course.courseId)
        findNavController().navigate(direction, extras)
    }

}