package com.bikcodeh.newsapp.ui.screen.viewmodel

import app.cash.turbine.test
import com.bikcodeh.newsapp.data.model.TopNewsArticle
import com.bikcodeh.newsapp.data.model.TopNewsResponse
import com.bikcodeh.newsapp.data.repository.Repository
import com.bikcodeh.newsapp.domain.common.Result
import com.bikcodeh.newsapp.util.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    lateinit var mainViewModel: MainViewModel

    @MockK(relaxed = true)
    lateinit var repository: Repository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(
            repository,
            UnconfinedTestDispatcher()
        )
    }

    @Test
    fun getMainState() {
        assertThat(mainViewModel.mainState.value).isInstanceOf(MainViewModel.NewsUiState::class.java)
        assertThat(mainViewModel.mainState.value).isEqualTo(MainViewModel.NewsUiState())
    }

    @Test
    fun getSearchQuery() {
        assertThat(mainViewModel.searchQuery.value).isEqualTo("")
    }

    @Test
    fun `search query should change its value and return the new one`() {
        mainViewModel.updateQuery("newQuery")
        assertThat(mainViewModel.searchQuery.value).isEqualTo("newQuery")
    }

    @Test
    fun getSourceName() {
        assertThat(mainViewModel.sourceName.value).isEqualTo("abc-news")
    }

    @Test
    fun `sourceName should change its value and return the new one`() {
        mainViewModel.updateSource("newSource")
        assertThat(mainViewModel.sourceName.value).isEqualTo("newSource")
    }

    @Test
    fun `getTopArticles should return an article`() = runTest {
        /** given */
        val response = TopNewsResponse(articles = listOf(TopNewsArticle(author = "OK")))
        coEvery { repository.getArticles() } answers   {
            Result.Success(response)
        }

        val results = arrayListOf<MainViewModel.NewsUiState>()

        val job = launch(UnconfinedTestDispatcher()) {
            mainViewModel.mainState.toList(results)
        }

        /** when */
        mainViewModel.getTopArticles()

        /** then */
        assertThat(results[0].isLoading).isFalse()
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[2].articles.count()).isEqualTo(1)
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].error).isNull()

        coVerify(exactly = 1) { repository.getArticles() }
        job.cancel()
    }

    @Test
    fun `getTopArticles should should return an error`() = runTest {
        /** given */
        val error = UnknownHostException()
        coEvery { repository.getArticles() } returns Result.Error(error)

        /** when */
        mainViewModel.getTopArticles()

        /** then */
        mainViewModel.mainState.test {
            val data = awaitItem()
            assertThat(data.isLoading).isFalse()
            assertThat(data.articles).isEmpty()
            assertThat(data.error).isEqualTo(error)
        }
        coVerify(exactly = 1) { repository.getArticles() }
    }

    @Test
    fun getArticlesByCategory() {
    }

    @Test
    fun onSelectedCategoryChanged() {
    }

    @Test
    fun getSearchArticles() {
    }

    @Test
    fun getArticlesBySource() {
    }

    @Test
    fun updateQuery() {
    }

    @Test
    fun updateSource() {
    }

    @Test
    fun clearSearch() = runTest {
        val response = TopNewsResponse(articles = listOf(TopNewsArticle(author = "Searched")))
        coEvery { repository.getSearchArticles("data") } returns Result.Success(response)

        mainViewModel.getSearchArticles("data")

        mainViewModel.mainState.test {
            val data = awaitItem()
            assertThat(data.searchedNews.count()).isEqualTo(1)
        }

        mainViewModel.clearSearch()

        mainViewModel.mainState.test {
            val data = awaitItem()
            assertThat(data.searchedNews.count()).isEqualTo(0)
        }
        coVerify { repository.getSearchArticles("data") }
    }
}