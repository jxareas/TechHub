package com.jxareas.techhub.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jxareas.techhub.data.cache.dao.CourseDao
import com.jxareas.techhub.data.cache.database.TechHubDatabase.Companion.DATABASE_VERSION
import com.jxareas.techhub.data.cache.model.CachedCourse

@Database(entities = [CachedCourse::class], version = DATABASE_VERSION, exportSchema = false)
abstract class TechHubDatabase : RoomDatabase() {

    abstract val courseDao: CourseDao

    companion object {
        internal const val DATABASE_VERSION : Int = 1
        internal const val DATABASE_NAME : String = "TechHub.db"
    }


}