package com.dumitrachecristian.surveyapp.di

import com.dumitrachecristian.surveyapp.network.ApiService
import com.dumitrachecristian.surveyapp.network.ApiServiceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideApiService(): ApiService {
        return ApiServiceProvider.getClient()
    }
}