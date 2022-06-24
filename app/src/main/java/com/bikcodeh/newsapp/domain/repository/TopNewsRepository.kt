package com.bikcodeh.newsapp.domain.repository

import com.bikcodeh.newsapp.data.model.TopNewsResponse
import com.bikcodeh.newsapp.domain.common.Result

interface TopNewsRepository {
    suspend fun getArticles(): Result<TopNewsResponse>
    suspend fun getArticlesByCategory(category: String): Result<TopNewsResponse>
    suspend fun getSearchArticles(query: String): Result<TopNewsResponse>
    suspend fun getArticlesBySource(source: String): Result<TopNewsResponse>
}