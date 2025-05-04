package uk.ac.tees.mad.e4294395.newsapp.data.api

import uk.ac.tees.mad.e4294395.newsapp.di.NetworkApiKey
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(@NetworkApiKey private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder().header("X-Api-Key", "52f75359874547b7a6df95de67a459e3")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}