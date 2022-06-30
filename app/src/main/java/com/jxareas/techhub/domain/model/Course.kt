package com.jxareas.techhub.domain.model

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
    var lastAccessed: Date? = null,
    var order: Int? = null,
) : DomainEntity
