package com.jxareas.techhub.di

import com.jxareas.techhub.data.api.service.CourseService
import com.jxareas.techhub.data.cache.dao.CourseDao
import com.jxareas.techhub.data.repository.CourseRepository
import com.jxareas.techhub.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideCourseRepository(dao : CourseDao, service : CourseService, dispatcher : DispatcherProvider) =
        CourseRepository(dao, service, dispatcher)
}