package com.example.newsapiclient.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsAPIServiceTest {
    private lateinit var service:NewsAPIService
    private lateinit var server:MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))// use MockWebServer's url, keep it empty to use default settings
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
            .create(NewsAPIService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    // MockWebServer can't read the response from the JSON file directly.
    // we need to create a file reader to read the contents of the JSON file and convert them into a string obj
    // after that, we need to add that String MockResponse to the queue of the MockWebServer
    // the first request to the MockWebServer will be replied with the first enqueued response, second with the second response and so on.
    fun enqueueMockResponse(fileName:String){
        // getResourceAsStream -> returns the specified resource of the class in the form of InputStream object.
        // classLoader -> loading Java classes dynamically to the JVM during runtime
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        // get the data source from the stream and set it into memory buffer
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getTopHeadlines_sentRequest_receivedExpected(){
        // check if our service function sends request to the server properly
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us",1).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=76a8e8001fe74310a7e5cd0488168193")
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize(){
        // check if the service function receives the response properly
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us",1).body()
            val articlesList = responseBody!!.articles
            assertThat(articlesList.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctContent(){
        // check if the service function receives the response properly
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us",1).body()
            val articlesList = responseBody!!.articles
            val article = articlesList[2]
            assertThat(article.author).isEqualTo("Brie Stimson")
            assertThat(article.url).isEqualTo("https://www.foxnews.com/health/ohio-county-reports-more-than-measles-cases-majority-countrys")
            assertThat(article.publishedAt).isEqualTo("2022-12-30T21:29:43Z")
        }
    }

}