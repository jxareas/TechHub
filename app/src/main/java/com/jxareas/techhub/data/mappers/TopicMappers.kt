package com.jxareas.techhub.data.mappers

import com.jxareas.techhub.data.api.dto.response.GetOneTopicResponse
import com.jxareas.techhub.data.cache.model.CachedTopic
import com.jxareas.techhub.domain.model.Topic

fun GetOneTopicResponse.toCachedTopic(): CachedTopic =
    CachedTopic(
        name = this.name,
        totalCourses = this.totalCourses,
        imageUrl = this.imageUrl,
        topicId = this.id,
    )

fun CachedTopic.toDomain(): Topic =
    Topic(topicId, name, totalCourses, imageUrl)
