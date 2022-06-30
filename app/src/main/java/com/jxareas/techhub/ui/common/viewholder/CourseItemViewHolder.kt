package com.jxareas.techhub.ui.common.viewholder

import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.R
import com.jxareas.techhub.databinding.ListItemCourseBinding
import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.ui.common.listeners.CourseAdapterListener
import com.jxareas.techhub.utils.extensions.loadImage

class CourseItemViewHolder(
    private val binding: ListItemCourseBinding,
    private val listener: CourseAdapterListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(course: Course) = binding.run {
        val transitionName = root.context.getString(R.string.card_item_transition)
        ViewCompat.setTransitionName(root, "$transitionName${course.courseId}")
        val steps = root.context.getString(
            R.string.course_steps,
            course.step.toString(),
            course.steps.toString()
        )
        textViewCourseName.text = course.name
        textViewCourseSteps.text = steps
        imageViewCourseImage.loadImage(course.coursePhoto, true)
        imageViewInstructorPhoto.loadImage(course.instructorPhoto)
        root.setOnClickListener { listener.onCourseClicked(root, course) }
    }

}
