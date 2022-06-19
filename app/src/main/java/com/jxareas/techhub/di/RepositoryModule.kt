package com.jxareas.techhub.di

import com.jxareas.techhub.data.repository.CourseRepository
import com.jxareas.techhub.data.repository.TopicRepository
import com.jxareas.techhub.data.repository.impl.CourseRepositoryImpl
import com.jxareas.techhub.data.repository.impl.TopicRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    @ViewModelScoped
    fun bindCourseRepository(repository: CourseRepositoryImpl): CourseRepository

    @Binds
    @ViewModelScoped
    fun bindTopicRepository(repository: TopicRepositoryImpl): TopicRepository


}