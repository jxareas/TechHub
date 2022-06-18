package com.jxareas.techhub.data.repository

import com.jxareas.techhub.data.api.service.CourseService
import com.jxareas.techhub.data.cache.dao.CourseDao
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.data.mappers.toCachedCourse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val courseDao: CourseDao,
    private val courseService: CourseService,
) {

    suspend fun loadCourses(onLoadingFinished: () -> Unit): Flow<List<CachedCourse>> = flow {
        var courses = courseDao.getAll()
        if (courses.isEmpty()) {
            val response = courseService.getCourses()
            courses = response.map { course ->
                course.toCachedCourse()
            }
            courseDao.insertAll(courses)
            emit(courses)
        } else emit(courses)

    }.onCompletion { onLoadingFinished() }.flowOn(Dispatchers.IO)

    suspend fun loadCourseById(id : Int) : Flow<CachedCourse> = flow {
        val course = courseDao.getById(id)
        emit(course)
    }.flowOn(Dispatchers.IO)

}