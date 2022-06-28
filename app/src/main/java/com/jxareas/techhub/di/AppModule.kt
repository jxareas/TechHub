package com.jxareas.techhub.di

import com.jxareas.techhub.utils.DefaultDispatchers
import com.jxareas.techhub.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDispatchers(): DispatcherProvider =
        DefaultDispatchers

}
