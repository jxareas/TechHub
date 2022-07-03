package com.jxareas.techhub.ui.topics

import android.graphics.Color
import android.graphics.Path
import android.os.Bundle
import android.transition.PatternPathMotion
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.OvershootInterpolator
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
            duration = resources.getLong(R.integer.anticipate_or_overshoot)
            scrimColor = Color.TRANSPARENT
            drawingViewId = R.id.nav_host_fragment_main
            interpolator = AnticipateOvershootInterpolator(.8f)
            pathMotion = PatternPathMotion(Path().apply {
                arcTo(0f, 0f, 1000f, 1000f, 270f, -180f, true)
            })
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        }
        sharedElementReturnTransition = MaterialContainerTransform().apply {
            duration = resources.getLong(R.integer.anticipate_or_overshoot)
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            interpolator = OvershootInterpolator(1f)
            scrimColor = Color.TRANSPARENT
            pathMotion = PatternPathMotion()
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
        binding.icBack.setOnClickListener {
            findNavController().navigateUp()
        }
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

    override fun onClicked(viewGroup: ViewGroup, item: Course) {
        val extras =
            FragmentNavigatorExtras(viewGroup to getString(R.string.course_detail_transition))
        val directions =
            CoursesByTopicFragmentDirections.coursesByTopicToDetail(item.courseId)
        findNavController().navigate(directions, extras)
    }

}
