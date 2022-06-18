package com.jxareas.techhub.adapter

import androidx.constraintlayout.widget.ConstraintLayout
import com.jxareas.techhub.data.cache.model.CachedCourse

@FunctionalInterface
fun interface CourseAdapterListener {
    fun onArtworkClicked(layout: ConstraintLayout, course: CachedCourse)
}