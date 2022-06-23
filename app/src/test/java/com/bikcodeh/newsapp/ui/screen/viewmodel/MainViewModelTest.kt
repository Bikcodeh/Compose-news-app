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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    lateinit var mainViewModel: MainViewModel

    private val dispatcher = UnconfinedTestDispatcher()

    @MockK(relaxed = true)
    lateinit var repository: Repository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(
            repository,
            dispatcher
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
        coEvery { repository.getArticles() } returns Result.Success(response)

        /** when */
        mainViewModel.getTopArticles()

        /** then */
        mainViewModel.mainState.test {
            val data = awaitItem()
            assertThat(data.isLoading).isFalse()
            assertThat(data.articles.first().author).isEqualTo("OK")
            assertThat(data.articles.count()).isEqualTo(1)
        }

        coVerify(exactly = 1) { repository.getArticles() }
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
    fun clearSearch() {
    }
}