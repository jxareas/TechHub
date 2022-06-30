package com.jxareas.techhub.data.cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.jxareas.techhub.data.cache.model.CachedTopic

@Dao
interface TopicDao : BaseDao<CachedTopic> {

    @Query("SELECT * FROM topics ORDER BY name")
    fun getAll(): List<CachedTopic>


}
