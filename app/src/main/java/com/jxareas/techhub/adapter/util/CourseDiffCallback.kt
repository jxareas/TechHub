package com.jxareas.techhub.adapter.util

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.techhub.data.cache.model.CachedCourse

object CourseDiffCallback : DiffUtil.ItemCallback<CachedCourse>() {
    override fun areItemsTheSame(oldItem: CachedCourse, newItem: CachedCourse): Boolean =
        oldItem.courseId == newItem.courseId

    override fun areContentsTheSame(oldItem: CachedCourse, newItem: CachedCourse): Boolean =
        oldItem.coursePhoto == newItem.coursePhoto
}