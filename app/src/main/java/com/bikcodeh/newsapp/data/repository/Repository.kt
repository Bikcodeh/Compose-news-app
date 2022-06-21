package com.bikcodeh.newsapp.data.repository

import com.bikcodeh.newsapp.ui.screen.home.NewsManager
import javax.inject.Inject

class Repository @Inject constructor(
    private val newsManager: NewsManager
) {
    suspend fun getArticles() = newsManager.getArticles("us")
    suspend fun getArticlesByCategory(category: String) = newsManager.getArticlesByCategory(category)
    suspend fun getSearchArticles(query: String) = newsManager.getSearchArticles(query)
    suspend fun getArticlesBySource(source: String) = newsManager.getArticlesBySource(source)
}