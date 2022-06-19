package com.jxareas.techhub.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jxareas.techhub.data.cache.dao.CourseDao
import com.jxareas.techhub.data.cache.dao.TopicDao
import com.jxareas.techhub.data.cache.database.TechHubDatabase.Companion.DATABASE_VERSION
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.data.cache.model.CachedTopic

@Database(entities = [CachedCourse::class, CachedTopic::class], version = DATABASE_VERSION, exportSchema = false)
abstract class TechHubDatabase : RoomDatabase() {

    abstract val courseDao: CourseDao

    abstract val topicDao : TopicDao

    companion object {
        internal const val DATABASE_VERSION : Int = 3
        internal const val DATABASE_NAME : String = "TechHub.db"
    }


}