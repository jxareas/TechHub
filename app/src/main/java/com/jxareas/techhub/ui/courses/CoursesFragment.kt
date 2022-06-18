package com.jxareas.techhub.ui.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jxareas.techhub.adapter.CourseCardAdapter
import com.jxareas.techhub.databinding.FragmentCoursesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoursesFragment : Fragment() {

    private var _binding: FragmentCoursesBinding? = null
    private val binding: FragmentCoursesBinding
        get() = _binding!!

    private val viewModel : CoursesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.courses.observe(viewLifecycleOwner) { courses ->
            (binding.recyclerViewCourses.adapter as CourseCardAdapter).submitList(courses)
        }
    }

    private fun setupRecyclerView() = binding.recyclerViewCourses.run {
        adapter = CourseCardAdapter { course ->
            val direction = CoursesFragmentDirections.coursesToDetailFragment(course.courseId)
            findNavController().navigate(direction)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}