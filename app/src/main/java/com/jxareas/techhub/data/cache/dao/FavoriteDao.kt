package com.jxareas.techhub.data.cache.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.jxareas.techhub.data.cache.model.CachedFavorite
import com.jxareas.techhub.data.cache.relations.FavoriteCourse

@Dao
interface FavoriteDao : BaseDao<CachedFavorite> {

    @Transaction
    @Query("SELECT * FROM favorites ORDER BY dateAdded")
    suspend fun getAll(): List<FavoriteCourse>

    @Query("DELETE from favorites WHERE courseId = :courseId")
    suspend fun removeFromFavorites(courseId: Int)
}
