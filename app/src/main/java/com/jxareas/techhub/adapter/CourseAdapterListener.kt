package com.jxareas.techhub.adapter

import android.view.ViewGroup
import com.jxareas.techhub.data.cache.model.CachedCourse

@FunctionalInterface
fun interface CourseAdapterListener {
    fun onArtworkClicked(layout: ViewGroup, course: CachedCourse)
}