package com.jxareas.techhub.ui.common.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.R
import com.jxareas.techhub.databinding.ListItemRelatedCourseBinding
import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.utils.extensions.loadImage

class RelatedCourseViewHolder(
    private val binding: ListItemRelatedCourseBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(course: Course) = binding.run {
        textViewCourseName.text = course.name
        textViewCourseSteps.text = itemView.context.getString(
            R.string.course_steps, course.step, course.steps
        )
        imageViewCourse.loadImage(course.coursePhoto)
        imageViewInstructorPhoto.loadImage(course.instructorPhoto)
    }

}
