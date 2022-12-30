package com.example.newsapiclient.data.repository.datasource_impl

import com.example.newsapiclient.data.api.NewsAPIService
import com.example.newsapiclient.data.model.APIResponse
import com.example.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService,
    private val country: String,
    private val page: Int
    ): NewsRemoteDataSource {
    override suspend fun getTopHeadlines(): Response<APIResponse> {
        return newsAPIService.getTopHeadlines(country, page)
    }
}