package com.jxareas.techhub.domain.model

import androidx.room.TypeConverters
import com.jxareas.techhub.data.cache.converters.DateConverter
import java.util.*

data class Course(
    val courseId: Int,
    val name: String,
    val description: String,
    var favorite: Boolean,
    val coursePhoto: String,
    val step: Int,
    val steps: Int,
    val instructorPhoto: String,
    val instructorName: String,
    val topicName: String,
    @TypeConverters(DateConverter::class)
    var lastAccessed: Date? = null
) : DomainEntity
