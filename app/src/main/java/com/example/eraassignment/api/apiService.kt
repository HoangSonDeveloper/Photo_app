package com.example.eraassignment.api

import com.example.eraassignment.BuildConfig
import com.example.eraassignment.model.SearchResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelsApi {
    @GET("search")
    suspend fun getImages(
        @Query("query") query: String = "",
        @Query("page") page: Int = 1,
        @Query("per_page") pageSize: Int = 30
    ): Response<SearchResponse>
}


class ApiService {
    private val API_KEY = BuildConfig.PEXELS_API_KEY
    private val BASE_URL = "https://api.pexels.com/v1/"

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(API_KEY))
        .addInterceptor(logging)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    val create: PexelsApi = retrofit.create(PexelsApi::class.java)
}