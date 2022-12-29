package com.example.newsapiclient.domain.use_case

import com.example.newsapiclient.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {
}