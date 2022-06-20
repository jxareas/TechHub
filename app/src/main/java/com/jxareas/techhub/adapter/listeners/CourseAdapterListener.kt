package com.jxareas.techhub.adapter.listeners

import android.view.ViewGroup
import com.jxareas.techhub.data.cache.model.CachedCourse

@FunctionalInterface
fun interface CourseAdapterListener {
    fun onCourseClicked(layout: ViewGroup, course: CachedCourse)
}