package com.jxareas.techhub.data.repository.impl

import com.jxareas.techhub.data.api.dto.response.GetOneTopicResponse
import com.jxareas.techhub.data.api.service.TopicService
import com.jxareas.techhub.data.cache.dao.TopicDao
import com.jxareas.techhub.data.cache.model.CachedTopic
import com.jxareas.techhub.data.mappers.toCachedTopic
import com.jxareas.techhub.data.repository.TopicRepository
import com.jxareas.techhub.utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class TopicRepositoryImpl @Inject constructor(
    private val dao: TopicDao,
    private val service: TopicService,
    private val dispatchers: DispatcherProvider
) : TopicRepository {

    override suspend fun getAllTopics(onLoadingFinished: () -> Unit): Flow<List<CachedTopic>> =
        flow {
            var topics = dao.getAll()
            if (topics.isEmpty()) {
                val response = service.getTopics()
                topics = response.map(GetOneTopicResponse::toCachedTopic)
                dao.insertAll(topics)
                emit(topics)
            } else emit(topics)

        }.onCompletion { onLoadingFinished() }.flowOn(dispatchers.io)
}