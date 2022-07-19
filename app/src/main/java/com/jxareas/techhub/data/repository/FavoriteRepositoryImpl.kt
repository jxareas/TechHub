package com.jxareas.techhub.data.repository

import com.jxareas.techhub.data.cache.dao.FavoriteDao
import com.jxareas.techhub.data.cache.model.CachedFavorite
import com.jxareas.techhub.data.mappers.toDomain
import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.domain.repository.FavoriteRepository
import com.jxareas.techhub.utils.DispatcherProvider
import com.jxareas.techhub.utils.extensions.getCurrentDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
    private val dispatchers: DispatcherProvider
) :
    FavoriteRepository {

    override suspend fun addOrRemoveFromFavorites(courseId: Int, shouldAdd: Boolean) {
        withContext(dispatchers.io) {
            if (shouldAdd)
                favoriteDao.insert(
                    CachedFavorite(
                        courseId = courseId,
                        dateAdded = getCurrentDateTime()
                    )
                )
            else favoriteDao.removeFromFavorites(courseId)
        }
    }

    override suspend fun addToFavorites(courseId: Int) {
        val course = CachedFavorite(courseId = courseId, dateAdded = getCurrentDateTime())
        withContext(dispatchers.io) { favoriteDao.insert(course) }
    }

    override suspend fun removeFromFavorites(courseId: Int) {
        withContext(dispatchers.io) { favoriteDao.removeFromFavorites(courseId) }
    }

    override suspend fun getFavoriteCourses(): Flow<List<Course>> = flow {
        emit(favoriteDao.getAll().map { it.toDomain() })
    }.flowOn(dispatchers.io)

}
