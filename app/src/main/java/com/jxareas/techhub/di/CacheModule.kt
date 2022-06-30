package com.jxareas.techhub.di

import android.content.Context
import androidx.room.Room
import com.jxareas.techhub.data.cache.database.TechHubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TechHubDatabase =
        Room
            .databaseBuilder(context, TechHubDatabase::class.java, TechHubDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideFavoriteDao(database: TechHubDatabase) =
        database.favoriteDao

    @Provides
    @Singleton
    fun provideCourseDao(database: TechHubDatabase) =
        database.courseDao

    @Provides
    @Singleton
    fun provideTopicDao(database: TechHubDatabase) =
        database.topicDao

}
