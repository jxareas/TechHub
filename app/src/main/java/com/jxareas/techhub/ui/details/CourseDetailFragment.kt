package com.jxareas.techhub.ui.details

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.jxareas.techhub.R
import com.jxareas.techhub.adapter.RelatedCourseAdapter
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.FragmentCourseDetailBinding
import com.jxareas.techhub.utils.extensions.getCurrentDateTime
import com.jxareas.techhub.utils.extensions.getLong
import com.jxareas.techhub.utils.extensions.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseDetailFragment : Fragment() {

    private var _binding: FragmentCourseDetailBinding? = null
    private val binding: FragmentCourseDetailBinding
        get() = _binding!!

    private val viewModel: CourseDetailViewModel by viewModels()
    private val args: CourseDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment_main
            duration =
                resources.getLong(com.google.android.material.R.integer.material_motion_duration_medium_2)
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            interpolator = FastOutSlowInInterpolator()
            scrimColor = Color.TRANSPARENT
        }
        sharedElementReturnTransition = MaterialContainerTransform().apply {
            fadeMode = MaterialContainerTransform.FADE_MODE_OUT
            interpolator = LinearOutSlowInInterpolator()
            scrimColor = Color.TRANSPARENT
            drawingViewId = R.id.nav_host_fragment_main
            duration =
                resources.getLong(com.google.android.material.R.integer.material_motion_duration_medium_1)
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
        adapter = RelatedCourseAdapter { course ->
            findNavController().navigate(
                CourseDetailFragmentDirections.actionCourseDetailFragmentSelf(
                    course.courseId
                )
            )
        }
    }

    private fun setupListeners() = binding.run {
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }


        iconExpandCoursePhoto.setOnClickListener {
            val transitionName = getString(R.string.poster_transition)
            val extras = FragmentNavigatorExtras(
                imageViewCoursePhoto to transitionName
            )
            val direction = CourseDetailFragmentDirections.detailToPoster(args.courseId)
            findNavController().navigate(direction, extras)
        }
    }

    private fun setupObservers() {
        viewModel.course.observe(viewLifecycleOwner) { cachedCourse ->
            cachedCourse?.let { course ->
                bindToView(course)
            }
        }

        viewModel.relatedCourses.observe(viewLifecycleOwner) { cachedCourses ->
            cachedCourses?.let { courses ->
                (binding.recyclerViewRelatedCourses.adapter as RelatedCourseAdapter).submitList(courses)
            }
        }

    }

    private fun bindToView(course: CachedCourse) = binding.run {
        course.lastAccessed = getCurrentDateTime()
        Log.d("SOMETHING", course.lastAccessed.toString())
        viewModel.onUpdate(course)
        textViewCourseName.text = course.name
        textViewCourseDescription.text = course.description
        textViewTopic.text = course.topicName
        textViewInstructorName.text = course.instructorName
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
            viewModel.onUpdate(course)
            it.isActivated = course.favorite
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}