package com.jxareas.techhub.data.repository

import com.jxareas.techhub.data.api.service.CourseService
import com.jxareas.techhub.data.cache.dao.CourseDao
import com.jxareas.techhub.data.mappers.toCached
import com.jxareas.techhub.data.mappers.toCourseWithFavorite
import com.jxareas.techhub.data.mappers.toDomain
import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.domain.repository.CourseRepository
import com.jxareas.techhub.utils.DispatcherProvider
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val courseDao: CourseDao,
    private val courseService: CourseService,
    private val dispatchers: DispatcherProvider,
) : CourseRepository {


    override suspend fun getAllCourses(
        onInit: () -> Unit,
        onError: (StatusCode?) -> Unit,
        onSuccess: () -> Unit
    ): Flow<List<Course>> = flow {
        var courses = courseDao.getAll()
        if (courses.isEmpty())
            courseService.fetchCourses()
                .suspendOnSuccess {
                    courses = data.map { it.toCourseWithFavorite() }
                    courseDao.insertAll(courses.map { it.course })
                    emit(courses.map { it.toDomain() })
                    onSuccess()
                }
                .suspendOnError {
                    onError(statusCode)
                }
                .suspendOnException {
                    onError(null)
                }
        else emit(courses.map { it.toDomain() }).also { onSuccess() }
    }
        .onStart { onInit() }
        .flowOn(dispatchers.io)

    override suspend fun getCoursesByName(course: String): Flow<List<Course>> = flow {
        emit(courseDao.getAllCoursesByName(course).map { it.toDomain() })
    }.flowOn(dispatchers.io)

    override suspend fun getRecentCourses(): Flow<List<Course>> = flow {
        emit(courseDao.getRecentCourses().map { it.toDomain() })
    }.flowOn(dispatchers.io)

    override suspend fun getCoursesByTopicName(topic: String): Flow<List<Course>> = flow {
        emit(courseDao.getCoursesByTopic(topic).map { it.toDomain() })
    }.flowOn(dispatchers.io)

    override suspend fun updateCourse(course: Course) {
        courseDao.update(course.toCached())
    }

    override suspend fun updateAccessedDate(course: Course) {
        course.lastAccessed?.let { courseDao.updateAccessedDate(it, course.courseId) }
    }

    override suspend fun getRelatedCourses(courseId: Int): Flow<List<Course>> = flow {
        emit(courseDao.getRelatedCourses(courseId).map { it.toDomain() })
    }.flowOn(dispatchers.io)

    override suspend fun getCourseById(courseId: Int): Flow<Course> = flow {
        val course = courseDao.getById(courseId).toDomain()
        emit(course)
    }.flowOn(dispatchers.io)

}
