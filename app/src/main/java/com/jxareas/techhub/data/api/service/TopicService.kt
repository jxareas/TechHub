package com.jxareas.techhub.data.api.service

import com.jxareas.techhub.data.api.constants.ApiConstants
import com.jxareas.techhub.data.api.dto.response.GetOneTopicResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface TopicService {

    @GET(ApiConstants.TOPIC_ENDPOINT)
    suspend fun fetchTopics(): ApiResponse<List<GetOneTopicResponse>>

}
