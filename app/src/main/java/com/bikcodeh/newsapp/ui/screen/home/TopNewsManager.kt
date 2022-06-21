package com.bikcodeh.newsapp.ui.screen.home

import com.bikcodeh.newsapp.data.model.TopNewsResponse
import com.bikcodeh.newsapp.data.remote.NewsService
import com.bikcodeh.newsapp.domain.common.Result
import com.bikcodeh.newsapp.domain.common.makeSafeRequest
import javax.inject.Inject

class NewsManager @Inject constructor(
    private val newsService: NewsService
) {
    suspend fun getArticles(country: String): Result<TopNewsResponse> {
        return makeSafeRequest {
            newsService.getTopArticles(country)
        }
    }

    suspend fun getArticlesByCategory(category: String): Result<TopNewsResponse> {
        return makeSafeRequest {
            newsService.getArticlesByCategory(category)
        }
    }

    suspend fun getArticlesBySource(source: String): Result<TopNewsResponse> {
        return makeSafeRequest {
            newsService.getArticlesBySources(source)
        }
    }

    suspend fun getSearchArticles(query: String): Result<TopNewsResponse> {
        return makeSafeRequest {
            newsService.getArticles(query)
        }
    }
}