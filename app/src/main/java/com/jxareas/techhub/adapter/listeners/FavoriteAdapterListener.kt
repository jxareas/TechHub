package com.jxareas.techhub.adapter.listeners

interface FavoriteAdapterListener : CourseAdapterListener {
    fun onFavoriteCourseSwiped(courseId : Int)
}
