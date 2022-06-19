package com.jxareas.techhub.data.mappers

import com.jxareas.techhub.data.api.dto.response.GetOneTopicResponse
import com.jxareas.techhub.data.cache.model.CachedTopic

fun GetOneTopicResponse.toCachedTopic(): CachedTopic =
    CachedTopic(
        name = this.name,
        totalCourses = this.totalCourses,
        imageUrl = this.imageUrl,
    )