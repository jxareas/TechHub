package com.jxareas.techhub.data.mappers

import com.jxareas.techhub.data.api.dto.response.GetOneCourseResponse
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.data.cache.relations.CourseWithFavorite
import com.jxareas.techhub.data.cache.relations.FavoriteCourse
import com.jxareas.techhub.domain.model.Course

fun GetOneCourseResponse.toCourseWithFavorite(): CourseWithFavorite =
    CourseWithFavorite(
        course = CachedCourse(
            name = this.name,
            description = this.description,
            coursePhoto = this.imageUrl,
            step = this.step,
            steps = this.steps,
            instructorName = this.instructor.fullName,
            instructorPhoto = this.instructor.photoPath,
            topicName = this.topic.name,
            courseId = this.id,
        ),
        favorite = null
    )


fun CachedCourse.toDomain(): Course =
    Course(
        courseId,
        name,
        description,
        false,
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
        coursePhoto,
        step,
        steps,
        instructorPhoto,
        instructorName,
        topicName
    )

fun FavoriteCourse.toDomain(): Course =
    Course(
        courseId = course.courseId,
        name = course.name,
        description = course.description,
        favorite = true,
        coursePhoto = course.coursePhoto,
        steps = course.steps,
        step = course.step,
        instructorPhoto = course.instructorPhoto,
        instructorName = course.instructorName,
        topicName = course.topicName,
        lastAccessed = course.lastAccessed,
        order = favorite.dateAdded.toString().toIntOrNull()
    )

fun CourseWithFavorite.toDomain(): Course =
    Course(
        courseId = course.courseId,
        name = course.name,
        description = course.description,
        favorite = favorite != null,
        coursePhoto = course.coursePhoto,
        steps = course.steps,
        step = course.step,
        instructorPhoto = course.instructorPhoto,
        instructorName = course.instructorName,
        topicName = course.topicName,
        lastAccessed = course.lastAccessed,
        order = favorite?.dateAdded.toString().toIntOrNull()
    )
