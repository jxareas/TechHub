package com.jxareas.techhub.data.repository

import com.jxareas.techhub.data.api.dto.response.GetOneTopicResponse
import com.jxareas.techhub.data.api.service.TopicService
import com.jxareas.techhub.data.cache.dao.TopicDao
import com.jxareas.techhub.data.cache.model.CachedTopic
import com.jxareas.techhub.data.mappers.toCachedTopic
import com.jxareas.techhub.data.mappers.toDomain
import com.jxareas.techhub.domain.model.Topic
import com.jxareas.techhub.domain.repository.TopicRepository
import com.jxareas.techhub.utils.DispatcherProvider
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class TopicRepositoryImpl @Inject constructor(
    private val dao: TopicDao,
    private val service: TopicService,
    private val dispatchers: DispatcherProvider,
) : TopicRepository {

    override suspend fun getAllTopics(
        onInit: () -> Unit,
        onError: (StatusCode?) -> Unit,
        onSuccess: () -> Unit,
    ): Flow<List<Topic>> =
        flow {
            var topics = dao.getAll()
            if (topics.isEmpty())
                service.fetchTopics()
                    .suspendOnSuccess {
                        topics = data.map(GetOneTopicResponse::toCachedTopic)
                        dao.insertAll(topics)
                        topics = dao.getAll()
                        emit(topics.map(CachedTopic::toDomain))
                        onSuccess()
                    }
                    .suspendOnError {
                        onError(statusCode)
                    }
                    .suspendOnException {
                        onError(null)
                    }
            else emit(topics.map(CachedTopic::toDomain)).also { onSuccess() }

        }.onStart { onInit() }
            .flowOn(dispatchers.io)
}
