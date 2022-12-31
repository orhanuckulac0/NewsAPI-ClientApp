package com.example.newsapiclient.presentation.dependency_injection

import android.app.Application
import com.example.newsapiclient.domain.use_case.GetNewsHeadlinesUseCase
import com.example.newsapiclient.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsViewModelFactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        app: Application,
        getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase
    ): NewsViewModelFactory{
        return NewsViewModelFactory(app, getNewsHeadlinesUseCase)
    }
}