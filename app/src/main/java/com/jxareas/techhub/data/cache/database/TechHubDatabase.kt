package com.jxareas.techhub.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jxareas.techhub.data.cache.converters.DateConverter
import com.jxareas.techhub.data.cache.dao.CourseDao
import com.jxareas.techhub.data.cache.dao.FavoriteDao
import com.jxareas.techhub.data.cache.dao.TopicDao
import com.jxareas.techhub.data.cache.database.TechHubDatabase.Companion.DATABASE_VERSION
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.data.cache.model.CachedFavorite
import com.jxareas.techhub.data.cache.model.CachedTopic

@Database(
    entities = [CachedCourse::class, CachedTopic::class, CachedFavorite::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class TechHubDatabase : RoomDatabase() {

    abstract val courseDao: CourseDao

    abstract val favoriteDao: FavoriteDao

    abstract val topicDao: TopicDao

    companion object {
        internal const val DATABASE_VERSION: Int = 10
        internal const val DATABASE_NAME: String = "TechHub.db"
    }


}
