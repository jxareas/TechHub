package com.jxareas.techhub.data.api.service

import com.jxareas.techhub.data.api.constants.ApiConstants
import com.jxareas.techhub.data.api.dto.response.GetOneCourseResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface CourseService {

    @GET(ApiConstants.COURSES_ENDPOINT)
    suspend fun fetchCourses(): ApiResponse<List<GetOneCourseResponse>>

}
