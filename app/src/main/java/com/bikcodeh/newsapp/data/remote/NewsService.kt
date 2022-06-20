package com.bikcodeh.newsapp.data.remote

import com.bikcodeh.newsapp.data.model.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    /*@GET("top-headlines")
    suspend fun getTopArticles(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): TopNewsResponse*/

    @GET("top-headlines")
    fun getTopArticles(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Call<TopNewsResponse>
}