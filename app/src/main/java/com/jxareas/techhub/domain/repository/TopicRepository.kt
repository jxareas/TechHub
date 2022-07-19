package com.jxareas.techhub.domain.repository

import com.jxareas.techhub.domain.model.Topic
import com.skydoves.sandwich.StatusCode
import kotlinx.coroutines.flow.Flow

interface TopicRepository {

    suspend fun getAllTopics(
        onInit: () -> Unit = {},
        onError: (StatusCode?) -> Unit = {},
        onSuccess: () -> Unit
    ): Flow<List<Topic>>
}
