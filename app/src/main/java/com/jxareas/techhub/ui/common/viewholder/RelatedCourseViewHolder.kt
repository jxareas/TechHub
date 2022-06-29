package com.jxareas.techhub.ui.common.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.jxareas.techhub.R
import com.jxareas.techhub.ui.common.listeners.CourseAdapterListener
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.databinding.ListItemRelatedCourseBinding
import com.jxareas.techhub.utils.extensions.loadImage

class RelatedCourseViewHolder(
    private val binding: ListItemRelatedCourseBinding,
    private val listener: CourseAdapterListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(course: CachedCourse) = binding.run {
        textViewCourseName.text = course.name
        val steps = root.context.getString(
            R.string.course_steps,
            course.step.toString(),
            course.steps.toString()
        )
        textViewCourseSteps.text = steps
        imageViewCourse.loadImage(course.coursePhoto)
        imageViewInstructorPhoto.loadImage(course.instructorPhoto)
        root.setOnClickListener { listener.onCourseClicked(root, course) }

    }

}
