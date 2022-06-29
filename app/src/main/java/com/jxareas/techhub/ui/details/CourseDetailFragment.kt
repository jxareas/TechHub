package com.jxareas.techhub.ui.details

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
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
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.jxareas.techhub.R
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.FragmentCourseDetailBinding
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
            duration = resources.getLong(R.integer.material_motion_duration_medium_2)
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            interpolator = LinearOutSlowInInterpolator()
            scrimColor = Color.TRANSPARENT
        }
        sharedElementReturnTransition = MaterialContainerTransform().apply {
            fadeMode = MaterialContainerTransform.FADE_MODE_OUT
            interpolator = FastOutSlowInInterpolator()
            pathMotion = MaterialArcMotion()
            duration = resources.getLong(R.integer.material_motion_duration_medium_2)
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

    private fun navigateToDetails(id: Int) {
        val directions = CourseDetailFragmentDirections.actionCourseDetailFragmentSelf(id)
        findNavController().navigate(directions)
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

    private fun bindToView(course: CachedCourse) = binding.run {
        course.lastAccessed = getCurrentDateTime()
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

    override fun onCourseClicked(viewGroup: ViewGroup, course: CachedCourse) {
        val directions =
            CourseDetailFragmentDirections.actionCourseDetailFragmentSelf(course.courseId)
        findNavController().navigate(directions)
    }

}
