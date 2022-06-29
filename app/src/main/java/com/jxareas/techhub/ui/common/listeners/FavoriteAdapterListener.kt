package com.jxareas.techhub.ui.common.listeners

interface FavoriteAdapterListener : CourseAdapterListener {
    fun onFavoriteCourseSwiped(courseId : Int)
    fun onFavoriteLongPressed(courseId: Int)
}
