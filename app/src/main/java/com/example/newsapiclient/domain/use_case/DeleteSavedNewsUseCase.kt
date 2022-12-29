package com.example.newsapiclient.domain.use_case

import com.example.newsapiclient.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {
}