package com.jxareas.techhub.data.mappers

import com.jxareas.techhub.data.api.dto.response.GetOneCourseResponse
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.domain.model.Course

fun GetOneCourseResponse.toCachedCourse(): CachedCourse =
    CachedCourse(
        name = this.name,
        description = this.description,
        coursePhoto = this.imageUrl,
        step = this.step,
        steps = this.steps,
        instructorName = this.instructor.fullName,
        instructorPhoto = this.instructor.photoPath,
        topicName = this.topic.name,
        courseId = this.id,
    )

fun CachedCourse.toDomain(): Course =
    Course(
        courseId,
        name,
        description,
        favorite,
        coursePhoto,
        step,
        steps,
        instructorPhoto,
        instructorName,
        topicName,
        lastAccessed
    )

fun Course.toCached(): CachedCourse =
    CachedCourse(
        courseId,
        name,
        description,
        favorite,
        coursePhoto,
        step,
        steps,
        instructorPhoto,
        instructorName,
        topicName
    )
