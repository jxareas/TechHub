package com.jxareas.techhub.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "courses"
)
data class CachedCourse(
    @PrimaryKey(autoGenerate = true)
    val courseId: Int = 0,
    val name: String,
    val description: String,
    val favorite: Boolean = false,
    val coursePhoto: String,
    val step: Int,
    val steps: Int,
    val instructorPhoto : String,
    val instructorName : String,
    val topicName : String,
) : CachedEntity