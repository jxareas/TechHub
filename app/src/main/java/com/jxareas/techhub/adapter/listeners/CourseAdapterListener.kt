package com.jxareas.techhub.adapter.listeners

import android.view.ViewGroup
import com.jxareas.techhub.data.cache.model.CachedCourse


@FunctionalInterface
interface CourseAdapterListener {
    fun onCourseClicked(viewGroup: ViewGroup, course: CachedCourse)
}
