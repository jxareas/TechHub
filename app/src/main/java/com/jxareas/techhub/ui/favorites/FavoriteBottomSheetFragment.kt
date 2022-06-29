package com.jxareas.techhub.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.transition.MaterialElevationScale
import com.jxareas.techhub.R
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.FragmentFavoriteBottomSheetBinding
import com.jxareas.techhub.utils.extensions.getLong
import com.jxareas.techhub.utils.extensions.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentFavoriteBottomSheetBinding? = null
    private val binding: FragmentFavoriteBottomSheetBinding
        get() = _binding!!

    private val args: FavoriteBottomSheetFragmentArgs by navArgs()
    private val viewModel: FavoriteBottomSheetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialElevationScale(true).apply {
            duration = resources.getLong(R.integer.material_motion_duration_medium_2)
            interpolator = FastOutSlowInInterpolator()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() = binding.run {
        root.setOnClickListener { view ->
            /*val transitionName = getString(R.string.course_detail_course_image_transition)
            val extras =
                FragmentNavigatorExtras(view to transitionName)*/
            val directions =
                FavoriteBottomSheetFragmentDirections.actionBottomSheetToDetails(args.courseId)
            findNavController().navigate(directions)
        }
    }

    private fun setupObservers() {
        viewModel.course.observe(viewLifecycleOwner) { course ->
            course?.let { newCourse -> bindData(newCourse) }
        }
    }

    private fun bindData(course: CachedCourse) = binding.run {
        imageViewCourse.loadImage(course.coursePhoto)
        imageViewInstructor.loadImage(course.instructorPhoto)
        textViewCourseTitle.text = course.name
        textViewCourseDescription.text = course.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
