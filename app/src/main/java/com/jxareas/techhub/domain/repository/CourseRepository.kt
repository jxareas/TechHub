package com.jxareas.techhub.domain.repository

import com.jxareas.techhub.domain.model.Course
import com.skydoves.sandwich.StatusCode
import kotlinx.coroutines.flow.Flow

interface CourseRepository {

    suspend fun getAllCourses(
        onInit: () -> Unit = {},
        onError: (StatusCode?) -> Unit = {},
        onSuccess: () -> Unit
    ): Flow<List<Course>>
    suspend fun getCoursesByName(course: String): Flow<List<Course>>
    suspend fun getRecentCourses(): Flow<List<Course>>
    suspend fun getCoursesByTopicName(topic: String): Flow<List<Course>>
    suspend fun updateCourse(course: Course)
    suspend fun updateAccessedDate(course: Course)
    suspend fun getRelatedCourses(courseId: Int): Flow<List<Course>>
    suspend fun getCourseById(courseId: Int): Flow<Course>


}
