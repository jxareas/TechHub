package com.jxareas.techhub.data.api.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class GetOneTopicResponse(
    val id : Int,
    val name : String,
    val totalCourses : Int,
    val imageUrl : String,
)