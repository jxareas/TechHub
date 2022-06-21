package com.jxareas.techhub.data.repository

import com.jxareas.techhub.data.cache.model.CachedCourse
import kotlinx.coroutines.flow.Flow

interface CourseRepository {

    suspend fun getAllCourses(onLoadingFinished: () -> Unit): Flow<List<CachedCourse>>
    suspend fun getCoursesByName(course : String) : Flow<List<CachedCourse>>
    suspend fun getRecentCourses() : Flow<List<CachedCourse>>
    suspend fun getCoursesByTopicName(topic : String) : Flow<List<CachedCourse>>
    suspend fun updateCourse(course : CachedCourse)
    suspend fun getFavoriteCourses(): Flow<List<CachedCourse>>
    suspend fun getRelatedCourses(courseId: Int): Flow<List<CachedCourse>>
    suspend fun getCourseById(courseId: Int): Flow<CachedCourse>


}