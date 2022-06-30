package com.jxareas.techhub.data.cache.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.jxareas.techhub.data.cache.constants.CacheConstants
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.data.cache.model.CachedFavorite

class FavoriteCourse(
    @Embedded
    val favorite: CachedFavorite,
    @Relation(
        entity = CachedCourse::class,
        parentColumn = CacheConstants.COURSE_KEY,
        entityColumn = CacheConstants.COURSE_KEY,
    )
    val course: CachedCourse
)
