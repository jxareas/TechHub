package com.jxareas.techhub.data.repository

import com.jxareas.techhub.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {

    suspend fun getAllCourses(onLoadingFinished: () -> Unit): Flow<List<Course>>
    suspend fun getCoursesByName(course: String): Flow<List<Course>>
    suspend fun getRecentCourses(): Flow<List<Course>>
    suspend fun getCoursesByTopicName(topic: String): Flow<List<Course>>
    suspend fun updateCourse(course: Course)
    suspend fun removeFromFavorites(courseId: Int)
    suspend fun getFavoriteCourses(): Flow<List<Course>>
    suspend fun getRelatedCourses(courseId: Int): Flow<List<Course>>
    suspend fun getCourseById(courseId: Int): Flow<Course>


}
