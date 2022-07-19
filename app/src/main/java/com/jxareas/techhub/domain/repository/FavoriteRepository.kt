package com.jxareas.techhub.domain.repository

import com.jxareas.techhub.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun addOrRemoveFromFavorites(courseId: Int, shouldAdd: Boolean)
    suspend fun addToFavorites(courseId: Int)
    suspend fun removeFromFavorites(courseId: Int)
    suspend fun getFavoriteCourses(): Flow<List<Course>>
}
