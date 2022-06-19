package com.jxareas.techhub.data.repository

import com.jxareas.techhub.data.cache.model.CachedTopic
import kotlinx.coroutines.flow.Flow

interface TopicRepository {

    suspend fun getAllTopics(onLoadingFinished : () -> Unit) : Flow<List<CachedTopic>>
}