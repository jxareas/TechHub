package com.jxareas.techhub.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jxareas.techhub.data.api.constants.ApiConstants
import com.jxareas.techhub.data.api.service.CourseService
import com.jxareas.techhub.data.api.service.TopicService
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()


    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_ENDPOINT)
            .client(client)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    @Singleton
    fun provideCourseService(retrofit: Retrofit): CourseService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideTopicService(retrofit: Retrofit): TopicService =
        retrofit.create()

}
