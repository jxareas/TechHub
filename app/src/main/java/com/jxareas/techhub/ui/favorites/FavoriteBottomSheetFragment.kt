package com.jxareas.techhub.ui.favorites

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jxareas.techhub.databinding.FragmentFavoriteBottomSheetBinding
import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.utils.extensions.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentFavoriteBottomSheetBinding? = null
    private val binding: FragmentFavoriteBottomSheetBinding
        get() = _binding!!


    private val args: FavoriteBottomSheetFragmentArgs by navArgs()
    private val viewModel: FavoriteBottomSheetViewModel by viewModels()

    companion object {
        internal const val ON_DISMISS_KEY = "ON_DISMISS"
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
        imageViewCourse.setOnClickListener {
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

    private fun bindData(course: Course) = binding.run {
        imageViewCourse.loadImage(course.coursePhoto)
        imageViewInstructor.loadImage(course.instructorPhoto)
        textViewCourseTitle.text = course.name
        textViewCourseDescription.text = course.description
        iconFavorites.isActivated = course.favorite

        iconFavorites.setOnClickListener {
            course.favorite = !course.favorite
            viewModel.onUpdate(course)
            it.isActivated = course.favorite
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        findNavController().previousBackStackEntry?.savedStateHandle?.set(ON_DISMISS_KEY, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
