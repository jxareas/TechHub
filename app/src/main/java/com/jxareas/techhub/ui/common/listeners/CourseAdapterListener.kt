package com.jxareas.techhub.ui.common.listeners

import android.view.ViewGroup
import com.jxareas.techhub.domain.model.Course


@FunctionalInterface
interface CourseAdapterListener {
    fun onCourseClicked(viewGroup: ViewGroup, course: Course)
}
