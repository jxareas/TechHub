package com.jxareas.techhub.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topics")
class CachedTopic(
    @PrimaryKey(autoGenerate = true)
    val topicId : Int = 0,
    val name : String,
    val totalCourses : Int,
    val imageUrl : String,
) : CachedEntity