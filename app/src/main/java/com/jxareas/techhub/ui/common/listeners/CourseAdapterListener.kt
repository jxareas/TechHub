package com.jxareas.techhub.ui.common.listeners

import android.view.ViewGroup
import com.jxareas.techhub.data.cache.model.CachedCourse


@FunctionalInterface
interface CourseAdapterListener {
    fun onCourseClicked(viewGroup: ViewGroup, course: CachedCourse)
}
