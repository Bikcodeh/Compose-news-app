package com.bikcodeh.newsapp.ui.screen.viewmodel

import app.cash.turbine.test
import com.bikcodeh.newsapp.data.model.TopNewsArticle
import com.bikcodeh.newsapp.data.model.TopNewsResponse
import com.bikcodeh.newsapp.data.repository.Repository
import com.bikcodeh.newsapp.domain.common.Result
import com.bikcodeh.newsapp.domain.model.ArticleCategory
import com.bikcodeh.newsapp.util.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
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
        coEvery { repository.getArticles() } answers {
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
    fun getArticlesByCategory() = runTest {
        /** Given */
        val response = TopNewsResponse(articles = listOf(TopNewsArticle(author = "OK")))
        coEvery { repository.getArticlesByCategory("action") } answers {
            Result.Success(response)
        }

        val results = arrayListOf<MainViewModel.NewsUiState>()

        val job = launch(UnconfinedTestDispatcher()) {
            mainViewModel.mainState.toList(results)
        }

        /** When */
        mainViewModel.getArticlesByCategory("action")

        /** Then */
        assertThat(results[0].isLoading).isFalse()
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[2].articlesByCategory.count()).isEqualTo(1)
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].error).isNull()

        coVerify(exactly = 1) { repository.getArticlesByCategory("action") }
        job.cancel()
    }

    @Test
    fun `getArticlesByCategory should return a error`() = runTest {
        /** Given */
        val error = Exception()
        coEvery { repository.getArticlesByCategory("action") } answers {
            Result.Error(error)
        }

        val results = arrayListOf<MainViewModel.NewsUiState>()

        val job = launch(UnconfinedTestDispatcher()) {
            mainViewModel.mainState.toList(results)
        }

        /** When */
        mainViewModel.getArticlesByCategory("action")

        /** Then */
        assertThat(results[0].isLoading).isFalse()
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[2].articlesByCategory.count()).isEqualTo(0)
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].error).isEqualTo(error)

        coVerify(exactly = 1) { repository.getArticlesByCategory("action") }
        job.cancel()
    }

    @Test
    fun onSelectedCategoryChanged() = runTest {
        /**  Given */
        val results = arrayListOf<MainViewModel.NewsUiState>()

        val job = launch(UnconfinedTestDispatcher()) {
            mainViewModel.mainState.toList(results)
        }

        val expected = ArticleCategory.TECHNOLOGY.categoryName

        /**  When */
        mainViewModel.onSelectedCategoryChanged(expected)

        /**  Then */
        assertThat(
            results[1].selectedCategory?.categoryName
        ).isEqualTo(expected)

        job.cancel()
    }

    @Test
    fun getSearchArticles() = runTest {
        /** Given */
        val response = TopNewsResponse(articles = listOf(TopNewsArticle(author = "OK")))
        coEvery { repository.getSearchArticles("business") } answers {
            Result.Success(response)
        }

        val results = arrayListOf<MainViewModel.NewsUiState>()

        val job = launch(UnconfinedTestDispatcher()) {
            mainViewModel.mainState.toList(results)
        }

        /**  When */
        mainViewModel.getSearchArticles("business")

        /**  Then */
        assertThat(results[0].isLoading).isFalse()
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[2].searchedNews.count()).isEqualTo(1)
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].error).isNull()

        coVerify(exactly = 1) { repository.getSearchArticles("business") }
        job.cancel()
    }

    @Test
    fun getSearchArticlesError() = runTest {
        /** Given */
        val error = Exception()
        coEvery { repository.getSearchArticles("business") } answers {
            Result.Error(error)
        }

        val results = arrayListOf<MainViewModel.NewsUiState>()

        val job = launch(UnconfinedTestDispatcher()) {
            mainViewModel.mainState.toList(results)
        }

        /**  When */
        mainViewModel.getSearchArticles("business")

        /**  Then */
        assertThat(results[0].isLoading).isFalse()
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[2].searchedNews.count()).isEqualTo(0)
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].error).isNotNull()
        assertThat(results[2].error).isEqualTo(error)

        coVerify(exactly = 1) { repository.getSearchArticles("business") }
        job.cancel()
    }

    @Test
    fun getArticlesBySource() = runTest {
        /** Given */
        val response = TopNewsResponse(articles = listOf(TopNewsArticle(author = "OK")))
        coEvery { repository.getArticlesBySource("abc-news") } answers {
            Result.Success(response)
        }

        val results = arrayListOf<MainViewModel.NewsUiState>()

        val job = launch(UnconfinedTestDispatcher()) {
            mainViewModel.mainState.toList(results)
        }

        /**  When */
        mainViewModel.getArticlesBySource()

        /**  Then */
        assertThat(results[0].isLoading).isFalse()
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[2].articlesBySource.count()).isEqualTo(1)
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].error).isNull()

        coVerify(exactly = 1) { repository.getArticlesBySource("abc-news") }
        job.cancel()
    }

    @Test
    fun getArticlesBySourceError() = runTest {
        /** Given */
        val error = Exception()
        coEvery { repository.getArticlesBySource("abc-news") } answers {
            Result.Error(error)
        }

        val results = arrayListOf<MainViewModel.NewsUiState>()

        val job = launch(UnconfinedTestDispatcher()) {
            mainViewModel.mainState.toList(results)
        }

        /**  When */
        mainViewModel.getArticlesBySource()

        /**  Then */
        assertThat(results[0].isLoading).isFalse()
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[2].articlesBySource.count()).isEqualTo(0)
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].error).isEqualTo(error)
        assertThat(results[2].error).isNotNull()

        coVerify(exactly = 1) { repository.getArticlesBySource("abc-news") }
        job.cancel()
    }

    @Test
    fun clearSearch() = runTest {
        /**  Given */
        val response = TopNewsResponse(articles = listOf(TopNewsArticle(author = "Searched")))
        coEvery { repository.getSearchArticles("data") } returns Result.Success(response)

        /**  When */
        mainViewModel.getSearchArticles("data")

        /**  Then */
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