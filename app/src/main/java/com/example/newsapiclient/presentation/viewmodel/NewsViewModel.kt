package com.example.newsapiclient.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapiclient.data.model.APIResponse
import com.example.newsapiclient.data.util.Resource
import com.example.newsapiclient.domain.use_case.GetNewsHeadlinesUseCase
import com.example.newsapiclient.domain.use_case.GetSearchedNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// use AndroidViewModel because I will need to use context while checking network availability of the user
// passing context instance on ViewModel can cause memory leaks
// AndroidViewModel comes with the application context

class NewsViewModel(
    private val app:Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase
    ): AndroidViewModel(app) {

    val newsHeadLines : MutableLiveData<Resource<APIResponse>> = MutableLiveData()
    val searchedNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsHeadLines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO){
        // use postValue instead of setValue when working with background thread
        newsHeadLines.postValue(Resource.Loading())

        try {
            if (isNetworkAvailable(app)){
                val apiResult = getNewsHeadlinesUseCase.execute(country,page)
                newsHeadLines.postValue(apiResult)
            }else{
                newsHeadLines.postValue(Resource.Error("Internet is not available"))
            }
        }catch (e:Exception){
            newsHeadLines.postValue(Resource.Error(e.message.toString()))
        }
    }

    @Suppress("DEPRECATION")
    fun isNetworkAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager?.run {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            connectivityManager?.run {
                connectivityManager.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }

    // search
    fun searchNews(country: String, searchQuery: String, page: Int) = viewModelScope.launch(Dispatchers.IO){
        searchedNews.postValue(Resource.Loading())

        try {
            if (isNetworkAvailable(app)){
                val response = getSearchedNewsUseCase.execute(country, searchQuery, page)
                searchedNews.postValue(response)

            }else{
                searchedNews.postValue(Resource.Error("No internet connection."))
            }
        }catch (e: Exception){
            searchedNews.postValue(Resource.Error(e.message.toString()))
        }
    }
}