package com.jxareas.techhub.ui.common.viewholder

import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.R
import com.jxareas.techhub.databinding.ListItemCourseCardBinding
import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.utils.extensions.loadImage

class CourseCardViewHolder(
    private val binding: ListItemCourseCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(course: Course) = with(binding) {
        val transitionName = itemView.context.getString(R.string.card_item_transition)
        ViewCompat.setTransitionName(itemView, "$transitionName${course.courseId}")
        textViewCourseName.text = course.name
        textViewCourseTopic.text = course.topicName
        textViewCourseSteps.text = course.steps.toString()
        imageViewCoursePhoto.loadImage(course.coursePhoto, true)
        imageViewInstructorPhoto.loadImage(course.instructorPhoto)
    }

}
