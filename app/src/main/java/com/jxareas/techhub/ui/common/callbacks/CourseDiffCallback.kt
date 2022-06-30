package com.jxareas.techhub.ui.common.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.techhub.domain.model.Course

object CourseDiffCallback : DiffUtil.ItemCallback<Course>() {
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean =
        oldItem.courseId == newItem.courseId

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean =
        oldItem.coursePhoto == newItem.coursePhoto

}
