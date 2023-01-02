package com.example.newsapiclient.data.repository

import com.example.newsapiclient.data.model.APIResponse
import com.example.newsapiclient.data.model.Article
import com.example.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.example.newsapiclient.data.util.Resource
import com.example.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

// connecting data and domain layers here
class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource
): NewsRepository {

    // getNewsHeadlines returns Resource<APIResponse> but,
    // NewsAPIService returns Response<APIResponse>
    // so we need to convert response to resource
    private fun responseToResource(response: Response<APIResponse>) : Resource<APIResponse>{
        if (response.isSuccessful){
            response.body()?.let {
                    result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    // override Domain Layer -> repository -> NewsRepository Interface functions below:
    override suspend fun getNewsHeadlines(country: String, pageSize: Int): Resource<APIResponse> {
        // convert response data coming from NewsRemoteDataSource Interface to Resource<APIResponse>
        return responseToResource(newsRemoteDataSource.getTopHeadlines(country, pageSize))
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        pageSize: Int,
    ): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getSearchedNews(country, searchQuery, pageSize))
    }

    override suspend fun saveNews(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNews(article: Article) {
        TODO("Not yet implemented")
    }

    override fun getSavedNews(): Flow<List<Article>> {
        TODO("Not yet implemented")
    }

}