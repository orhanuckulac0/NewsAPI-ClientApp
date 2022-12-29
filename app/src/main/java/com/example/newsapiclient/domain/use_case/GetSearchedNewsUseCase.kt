package com.example.newsapiclient.domain.use_case

import com.example.newsapiclient.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {
}