package com.jxareas.techhub.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.FragmentCourseDetailBinding
import com.jxareas.techhub.extensions.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseDetailFragment : Fragment() {

    private var _binding: FragmentCourseDetailBinding? = null
    private val binding: FragmentCourseDetailBinding
        get() = _binding!!

    private val viewModel : CourseDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.course.observe(viewLifecycleOwner) { cachedCourse ->
            cachedCourse?.let { course ->
                bindToView(course)
            }

        }
    }

    private fun bindToView(course: CachedCourse) = binding.run {
        textViewCourseName.text = course.name
        textViewCourseDescription.text = course.description
        textViewTopic.text = course.topicName
        textViewInstructorName.text = course.instructorName
        imageViewCoursePhoto.loadImage(course.coursePhoto)
        imageViewInstructorPhoto.loadImage(course.instructorPhoto)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}