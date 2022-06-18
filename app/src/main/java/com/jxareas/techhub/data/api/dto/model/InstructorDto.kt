package com.jxareas.techhub.data.api.dto.model

import kotlinx.serialization.Serializable

@Serializable
data class InstructorDto(
    val fullName: String,
    val photoPath: String,
)