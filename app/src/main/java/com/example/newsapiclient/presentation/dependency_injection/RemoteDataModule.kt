package com.example.newsapiclient.presentation.dependency_injection

import com.example.newsapiclient.data.api.NewsAPIService
import com.example.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.example.newsapiclient.data.repository.datasource_impl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsAPIService: NewsAPIService): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsAPIService)
    }
}