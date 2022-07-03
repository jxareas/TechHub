package com.jxareas.techhub.domain.model

data class Topic(
    val topicId: Int,
    val name: String,
    val totalCourses: Int,
    val imageUrl: String,
) : DomainEntity
