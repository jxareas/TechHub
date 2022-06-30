package com.jxareas.techhub.ui.topics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.jxareas.techhub.R
import com.jxareas.techhub.databinding.FragmentCoursesByTopicBinding
import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.ui.common.adapters.CourseListAdapter
import com.jxareas.techhub.ui.common.listeners.CourseAdapterListener
import com.jxareas.techhub.utils.animation.SpringAddItemAnimator
import com.jxareas.techhub.utils.extensions.getLong
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoursesByTopicFragment : Fragment(), CourseAdapterListener {

    private var _binding: FragmentCoursesByTopicBinding? = null
    private val binding: FragmentCoursesByTopicBinding
        get() = _binding!!

    private val coursesAdapter = CourseListAdapter(this)
    private val args: CoursesByTopicFragmentArgs by navArgs()
    private val viewModel: CoursesByTopicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = resources.getLong(R.integer.material_motion_duration_long_2)
            scrimColor = Color.TRANSPARENT
            drawingViewId = R.id.nav_host_fragment_main
            interpolator = FastOutSlowInInterpolator()
            pathMotion = MaterialArcMotion()
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        }
        sharedElementReturnTransition = MaterialContainerTransform().apply {
            duration = resources.getLong(R.integer.material_motion_duration_long_2)
            interpolator = LinearOutSlowInInterpolator()
            scrimColor = Color.TRANSPARENT
            pathMotion = MaterialArcMotion()
        }
    }

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
        setupViews()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupViews() {
        binding.textViewTopicName.text = args.topic
    }

    private fun setupRecyclerView() = binding.recyclerViewCourses.run {
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

    override fun onCourseClicked(viewGroup: ViewGroup, course: Course) {
        val extras =
            FragmentNavigatorExtras(viewGroup to getString(R.string.course_detail_course_image_transition))
        val directions =
            CoursesByTopicFragmentDirections.coursesByTopicToDetail(course.courseId)
        findNavController().navigate(directions, extras)
    }

}
