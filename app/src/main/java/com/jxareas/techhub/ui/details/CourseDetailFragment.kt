package com.jxareas.techhub.ui.details

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.PatternPathMotion
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.jxareas.techhub.R
import com.jxareas.techhub.databinding.FragmentCourseDetailBinding
import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.ui.common.adapters.RelatedCoursesListAdapter
import com.jxareas.techhub.ui.common.listeners.CourseAdapterListener
import com.jxareas.techhub.utils.extensions.getCurrentDateTime
import com.jxareas.techhub.utils.extensions.getLong
import com.jxareas.techhub.utils.extensions.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseDetailFragment : Fragment(), CourseAdapterListener {

    private var _binding: FragmentCourseDetailBinding? = null
    private val binding: FragmentCourseDetailBinding
        get() = _binding!!

    private val relatedCoursesAdapter = RelatedCoursesListAdapter(this)
    private val viewModel: CourseDetailViewModel by viewModels()
    private val args: CourseDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment_main
            duration = resources.getLong(R.integer.material_motion_duration_medium_2)
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            interpolator = FastOutSlowInInterpolator()
            pathMotion = PatternPathMotion()
            scrimColor = Color.TRANSPARENT
        }
        sharedElementReturnTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment_main
            scrimColor = Color.TRANSPARENT
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            fadeProgressThresholds = MaterialContainerTransform.ProgressThresholds(.1f, .2f)
            interpolator = FastOutSlowInInterpolator()
            pathMotion = MaterialArcMotion()
            duration = resources.getLong(R.integer.material_motion_duration_medium_1)
        }
        postponeEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }


    private fun setupRecyclerView() = binding.recyclerViewRelatedCourses.run {
        adapter = relatedCoursesAdapter
    }

    private fun setupListeners() = binding.run {
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        iconExpandCoursePhoto.setOnClickListener {
            val transitionName = getString(R.string.poster_transition)
            val extras = FragmentNavigatorExtras(imageViewCoursePhoto to transitionName)
            val direction = CourseDetailFragmentDirections.detailToPoster(args.courseId)
            findNavController().navigate(direction, extras)
        }
    }

    private fun setupObservers() {
        viewModel.course.observe(viewLifecycleOwner) { currentCourse ->
            currentCourse?.let { newCourse ->
                bindToView(newCourse)
            }
        }

        viewModel.relatedCourses.observe(viewLifecycleOwner) { relatedCourses ->
            relatedCourses?.let { newCourses ->
                relatedCoursesAdapter.submitList(newCourses)
            }
        }

    }

    private fun bindToView(course: Course) = binding.run {
        course.lastAccessed = getCurrentDateTime()
        viewModel.onUpdate(course)
        textViewCourseName.text = course.name
        textViewCourseDescription.text = course.description
        textViewTopic.text = course.topicName
        textViewInstructorName.text = course.instructorName
        textViewCourseStepsInfo.text =
            getString(R.string.course_steps_information, course.step, course.steps)
        iconFavorites.isActivated = course.favorite

        Glide.with(imageViewCoursePhoto)
            .load(course.coursePhoto)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean = false

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean = false.also { startPostponedEnterTransition() }

            }).into(imageViewCoursePhoto)
        imageViewInstructorPhoto.loadImage(course.instructorPhoto)

        iconFavorites.setOnClickListener {
            course.favorite = !course.favorite
            viewModel.addOrRemoveFromFavorites(course)
            it.isActivated = course.favorite
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClicked(viewGroup: ViewGroup, item: Course) {
        val directions =
            CourseDetailFragmentDirections.actionCourseDetailFragmentSelf(item.courseId)
        findNavController().navigate(directions)
    }

}
