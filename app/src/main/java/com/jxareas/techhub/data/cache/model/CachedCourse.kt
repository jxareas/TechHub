package com.jxareas.techhub.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.jxareas.techhub.data.cache.converters.DateConverter
import java.util.*

@Entity(
    tableName = "courses"
)
data class CachedCourse(
    @PrimaryKey(autoGenerate = true)
    val courseId: Int = 0,
    val name: String,
    val description: String,
    val coursePhoto: String,
    val step: Int,
    val steps: Int,
    val instructorPhoto: String,
    val instructorName: String,
    val topicName: String,
    @TypeConverters(DateConverter::class)
    var lastAccessed: Date? = null,
) : CachedEntity
