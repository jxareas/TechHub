package com.jxareas.techhub.data.cache.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.jxareas.techhub.data.cache.constants.CacheConstants
import com.jxareas.techhub.data.cache.converters.DateConverter
import java.util.*

@Entity(
    tableName = "favorites",
    foreignKeys = [
        ForeignKey(
            entity = CachedCourse::class,
            parentColumns = [CacheConstants.COURSE_KEY],
            childColumns = [CacheConstants.COURSE_KEY],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index("courseId", unique = true)]
)
data class CachedFavorite(
    @PrimaryKey(autoGenerate = true)
    val favoriteId: Int = 0,
    val courseId: Int,
    @TypeConverters(DateConverter::class)
    var dateAdded: Date
) : CachedEntity
