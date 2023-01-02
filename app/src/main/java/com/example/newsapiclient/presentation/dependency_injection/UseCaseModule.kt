package com.example.newsapiclient.presentation.dependency_injection

import com.example.newsapiclient.domain.repository.NewsRepository
import com.example.newsapiclient.domain.use_case.GetNewsHeadlinesUseCase
import com.example.newsapiclient.domain.use_case.GetSearchedNewsUseCase
import com.example.newsapiclient.domain.use_case.SaveNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetNewsHeadlinesUseCase(newsRepository: NewsRepository): GetNewsHeadlinesUseCase{
        return GetNewsHeadlinesUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSearchedNewsUseCase(newsRepository: NewsRepository): GetSearchedNewsUseCase{
        return GetSearchedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideSaveNewsUseCase(newsRepository: NewsRepository): SaveNewsUseCase{
        return SaveNewsUseCase(newsRepository)
    }
}
