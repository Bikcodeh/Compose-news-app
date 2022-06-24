package com.bikcodeh.newsapp.data.repository

import com.bikcodeh.newsapp.data.model.TopNewsResponse
import com.bikcodeh.newsapp.data.remote.NewsService
import com.bikcodeh.newsapp.domain.common.Result
import com.bikcodeh.newsapp.domain.common.makeSafeRequest
import com.bikcodeh.newsapp.domain.repository.TopNewsRepository
import javax.inject.Inject

class TopNewsRepositoryImpl @Inject constructor(
    private val newsService: NewsService
) : TopNewsRepository {

    override suspend fun getArticles(): Result<TopNewsResponse> {
        return makeSafeRequest {
            newsService.getTopArticles("us")
        }
    }

    override suspend fun getArticlesByCategory(category: String): Result<TopNewsResponse> {
        return makeSafeRequest {
            newsService.getArticlesByCategory(category)
        }
    }

    override suspend fun getSearchArticles(query: String): Result<TopNewsResponse> {
        return makeSafeRequest {
            newsService.getArticles(query)
        }
    }

    override suspend fun getArticlesBySource(source: String): Result<TopNewsResponse> {
        return makeSafeRequest {
            newsService.getArticlesBySources(source)
        }
    }
}