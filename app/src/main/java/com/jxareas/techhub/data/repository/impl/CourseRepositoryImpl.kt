package com.jxareas.techhub.data.repository.impl

import com.jxareas.techhub.data.api.dto.response.GetOneCourseResponse
import com.jxareas.techhub.data.api.service.CourseService
import com.jxareas.techhub.data.cache.dao.CourseDao
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.data.mappers.toCachedCourse
import com.jxareas.techhub.data.repository.CourseRepository
import com.jxareas.techhub.utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val courseDao: CourseDao,
    private val courseService: CourseService,
    private val dispatchers: DispatcherProvider,
) : CourseRepository {

    override suspend fun getAllCourses(onLoadingFinished: () -> Unit): Flow<List<CachedCourse>> = flow {
        var courses = courseDao.getAll()
        if (courses.isEmpty()) {
            val response = courseService.getCourses()
            courses = response.map(GetOneCourseResponse::toCachedCourse)
            courseDao.insertAll(courses)
            emit(courses)
        } else emit(courses)

    }.onCompletion { onLoadingFinished() }.flowOn(dispatchers.io)

    override suspend fun updateCourse(course: CachedCourse) {
        courseDao.update(course)
    }

    override suspend fun getFavoriteCourses() : Flow<List<CachedCourse>> = flow {
        emit(courseDao.getFavorites())
    }.flowOn(dispatchers.io)

    override suspend fun getRelatedCourses(courseId: Int): Flow<List<CachedCourse>> = flow {
        emit(courseDao.getRelatedCourses(courseId))
    }.flowOn(dispatchers.io)

    override suspend fun getCourseById(courseId: Int): Flow<CachedCourse> = flow {
        val course = courseDao.getById(courseId)
        emit(course)
    }.flowOn(dispatchers.io)

}