package com.jxareas.techhub.data.api.dto.response

import com.jxareas.techhub.data.api.dto.model.InstructorDto
import com.jxareas.techhub.data.api.dto.model.TopicDto
import kotlinx.serialization.Serializable

@Serializable
data class GetOneCourseResponse(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val imageContentDesc: String,
    val step: Int,
    val steps: Int,
    val instructor: InstructorDto,
    val topic: TopicDto,
)