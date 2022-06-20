package com.bikcodeh.newsapp.domain.repository

import com.bikcodeh.newsapp.data.model.TopNewsResponse
import com.bikcodeh.newsapp.domain.common.Result

interface TopNewsRepository {
    suspend fun getArticles(): Result<TopNewsResponse>
}