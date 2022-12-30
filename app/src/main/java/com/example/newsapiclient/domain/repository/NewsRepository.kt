package com.example.newsapiclient.domain.repository

import com.example.newsapiclient.data.model.APIResponse
import com.example.newsapiclient.data.model.Article
import com.example.newsapiclient.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    // functions for network communication
    suspend fun getNewsHeadlines(): Resource<APIResponse>
    suspend fun getSearchedNews(searchQuery: String): Resource<APIResponse>

    // functions related to local db
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    // in the viewModel class, we will collect this stream of data flow and emit it as live data
    // since this fun returns a data stream, don't need to write as suspend fun
    fun getSavedNews(): Flow<List<Article>>
}