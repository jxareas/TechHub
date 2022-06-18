package com.jxareas.techhub.data.mappers

import com.jxareas.techhub.data.api.dto.response.GetOneCourseResponse
import com.jxareas.techhub.data.cache.model.CachedCourse

fun GetOneCourseResponse.toCachedCourse(): CachedCourse =
    CachedCourse(
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl,
        step = this.step,
        steps = this.steps,
        instructorName = this.instructor.fullName,
        instructorPhoto = this.instructor.photoPath,
        topicName = this.topic.name
    )
