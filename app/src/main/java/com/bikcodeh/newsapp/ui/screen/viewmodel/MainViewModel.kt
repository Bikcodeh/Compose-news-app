package com.bikcodeh.newsapp.ui.screen.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcodeh.newsapp.data.model.TopNewsArticle
import com.bikcodeh.newsapp.data.repository.Repository
import com.bikcodeh.newsapp.di.IoDispatcher
import com.bikcodeh.newsapp.domain.common.fold
import com.bikcodeh.newsapp.domain.model.ArticleCategory
import com.bikcodeh.newsapp.domain.model.getArticleCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _mainState = MutableStateFlow(NewsUiState())
    val mainState: StateFlow<NewsUiState>
        get() = _mainState.asStateFlow()

    val searchQuery: MutableState<String> = mutableStateOf("")
    val sourceName: MutableState<String> = mutableStateOf("abc-news")

    fun getTopArticles() {
        viewModelScope.launch(dispatcher) {
            _mainState.update { currentState -> currentState.copy(isLoading = true) }
            repository.getArticles()
                .fold(
                    onSuccess = {
                        _mainState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                articles = it?.articles ?: emptyList()
                            )
                        }
                    },
                    onFailure = {
                        _mainState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                error = it
                            )
                        }
                    }
                )
        }
    }

    fun getArticlesByCategory(category: String) {
        viewModelScope.launch(dispatcher) {
            _mainState.update { it.copy(isLoading = true) }
            repository.getArticlesByCategory(category)
                .fold(
                    onFailure = {
                        _mainState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                error = it
                            )
                        }
                    },
                    onSuccess = {
                        _mainState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                articlesByCategory = it?.articles ?: emptyList()
                            )
                        }
                    }
                )
        }
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category)
        _mainState.update { it.copy(selectedCategory = newCategory) }
    }

    fun getSearchArticles(query: String) {
        viewModelScope.launch(dispatcher) {
            repository.getSearchArticles(query)
                .fold(
                    onSuccess = {
                        _mainState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                searchedNews = it?.articles ?: emptyList()
                            )
                        }
                    },
                    onFailure = {
                        _mainState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                error = it
                            )
                        }
                    }
                )
        }
    }

    fun getArticlesBySource() {
        viewModelScope.launch(dispatcher) {
            repository.getArticlesBySource(sourceName.value)
                .fold(
                    onSuccess = {
                        _mainState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                articlesBySource = it?.articles ?: emptyList()
                            )
                        }
                    },
                    onFailure = {
                        _mainState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                error = it
                            )
                        }
                    }
                )
        }
    }

    fun updateQuery(query: String) {
        searchQuery.value = query
    }

    fun updateSource(source: String) {
        sourceName.value = source
    }

    fun clearSearch() {
        _mainState.update { currentState -> currentState.copy(searchedNews = emptyList()) }
    }

    data class NewsUiState(
        val isLoading: Boolean = false,
        val articles: List<TopNewsArticle> = emptyList(),
        val error: Exception? = null,
        val articlesByCategory: List<TopNewsArticle> = emptyList(),
        val selectedCategory: ArticleCategory? = null,
        val searchedNews: List<TopNewsArticle> = emptyList(),
        val articlesBySource: List<TopNewsArticle> = emptyList()
    )
}