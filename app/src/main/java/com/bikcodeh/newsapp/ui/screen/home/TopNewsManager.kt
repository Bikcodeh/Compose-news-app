package com.bikcodeh.newsapp.ui.screen.home

import android.util.Log
import androidx.compose.runtime.*
import com.bikcodeh.newsapp.data.model.TopNewsResponse
import com.bikcodeh.newsapp.data.remote.Api
import com.bikcodeh.newsapp.data.remote.NewsService
import com.bikcodeh.newsapp.domain.common.Result
import com.bikcodeh.newsapp.domain.common.makeSafeRequest
import com.bikcodeh.newsapp.domain.model.ArticleCategory
import com.bikcodeh.newsapp.domain.model.getArticleCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NewsManager @Inject constructor(
    private val newsService: NewsService
) {

    private val _newsResponse =
        mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _newsResponse
        }

    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)

    private val _getArticleByCategory =
        mutableStateOf(TopNewsResponse())

    val getArticleByCategory: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleByCategory
        }

    private val _getArticleBySource =
        mutableStateOf(TopNewsResponse())

    val getArticleBySource: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleBySource
        }

    private val _searchedNews =
        mutableStateOf(TopNewsResponse())

    val searchedNews: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _searchedNews
        }

    val sourceName = mutableStateOf("abc-news")

    val query = mutableStateOf("")

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

    fun getArticlesBySource() {
        val service = Api.retrofitService.getArticlesBySources(sourceName.value)
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful) {
                    _getArticleBySource.value = response.body()!!
                    Log.d("Category", "${_getArticleBySource.value}")
                } else {
                    Log.d("error", "${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }
        })
    }

    fun getSearchArticles(query: String) {
        val service = Api.retrofitService.getArticles(query)
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful) {
                    _searchedNews.value = response.body()!!
                    Log.d("Category", "${_searchedNews.value}")
                } else {
                    Log.d("error", "${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }
        })
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category)
        selectedCategory.value = newCategory
    }
}