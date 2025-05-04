package uk.ac.tees.mad.e4294395.newsapp.data.api

import uk.ac.tees.mad.e4294395.newsapp.data.model.NewsSourcesResponse
import uk.ac.tees.mad.e4294395.newsapp.data.model.TopHeadlinesResponse
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET("everything")
    suspend fun getEverything(@Query("q") q: String): TopHeadlinesResponse

    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String): TopHeadlinesResponse

    @GET("top-headlines")
    suspend fun getTopHeadlinesPagination(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): TopHeadlinesResponse

    @GET("top-headlines/sources")
    suspend fun getNewsSources(): NewsSourcesResponse

    @GET("top-headlines")
    suspend fun getNewsBySources(@Query("sources") sources: String): TopHeadlinesResponse

    @GET("top-headlines")
    suspend fun getNewsByCountry(@Query("country") country: String): TopHeadlinesResponse

    @GET("top-headlines")
    suspend fun getNewsByLanguage(@Query("language") language: String): TopHeadlinesResponse

}