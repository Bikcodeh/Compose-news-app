package com.bikcodeh.newsapp.data.repository

import com.bikcodeh.newsapp.data.model.TopNewsArticle
import com.bikcodeh.newsapp.data.model.TopNewsResponse
import com.bikcodeh.newsapp.data.remote.NewsService
import com.bikcodeh.newsapp.domain.common.Result
import com.bikcodeh.newsapp.domain.repository.TopNewsRepository
import com.bikcodeh.newsapp.util.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class TopNewsRepositoryImplTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    lateinit var topNewsRepository: TopNewsRepository

    @RelaxedMockK
    lateinit var newsService: NewsService

    private val expected = TopNewsResponse(
        status = "OK",
        totalResults = 1,
        articles = listOf(TopNewsArticle(author = "test"))
    )

    private val error = Exception("exception")

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        topNewsRepository = TopNewsRepositoryImpl(
            newsService
        )
    }

    @Test
    fun getArticles() = runTest {
        /** Given */
        val expected = TopNewsResponse(
            status = "OK",
            totalResults = 1,
            articles = listOf(TopNewsArticle(author = "test"))
        )
        coEvery { newsService.getTopArticles("us") } answers { expected }

        /** When */
        val result = topNewsRepository.getArticles()

        /** Then */
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data.status).isEqualTo("OK")
        assertThat(result.data.articles?.count()).isEqualTo(1)
        assertThat((result).data.articles?.first()?.author).isEqualTo("test")
        coVerify(exactly = 1) { newsService.getTopArticles("us") }
    }

    @Test
    fun `getArticles should return a error result catching exception`() = runTest {
        /** Given */
        coEvery { newsService.getTopArticles("us") } throws error

        /** When */
        val result = topNewsRepository.getArticles()

        /** Then */
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).error.message).isEqualTo("exception")
        coVerify(exactly = 1) { newsService.getTopArticles("us") }
    }

    @Test
    fun `getArticles should return a error result catching unknown host exception`() = runTest {
        val error = UnknownHostException("without_host")
        /** Given */
        coEvery { newsService.getTopArticles("us") } throws error

        /** When */
        val result = topNewsRepository.getArticles()

        /** Then */
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).error).isInstanceOf(UnknownHostException::class.java)
        assertThat((result).error.message).isEqualTo("without_host")
        coVerify(exactly = 1) { newsService.getTopArticles("us") }
    }

    @Test
    fun getArticlesByCategory() = runTest {
        /** Given */
        coEvery { newsService.getArticlesByCategory("business") } answers { expected }

        /** When */
        val result = topNewsRepository.getArticlesByCategory("business")

        /** Then */
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data.status).isEqualTo("OK")
        assertThat(result.data.articles?.count()).isEqualTo(1)
        assertThat((result).data.articles?.first()?.author).isEqualTo("test")
        coVerify(exactly = 1) { newsService.getArticlesByCategory("business") }
    }

    @Test
    fun `getArticlesByCategory should return a error result`() = runTest {
        /** Given */
        coEvery { newsService.getArticlesByCategory("business") } throws error

        /** When */
        val result = topNewsRepository.getArticlesByCategory("business")

        /** Then */
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).error).isInstanceOf(Exception::class.java)
        assertThat((result).error.message).isEqualTo("exception")
        coVerify(exactly = 1) { newsService.getArticlesByCategory("business") }
    }

    @Test
    fun getSearchArticles() = runTest {
        /** Given */
        coEvery { newsService.getArticles("business") } answers { expected }

        /** When */
        val result = topNewsRepository.getSearchArticles("business")

        /** Then */
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data.status).isEqualTo("OK")
        assertThat(result.data.articles?.count()).isEqualTo(1)
        assertThat((result).data.articles?.first()?.author).isEqualTo("test")
        coVerify(exactly = 1) { newsService.getArticles("business") }
    }

    @Test
    fun `getSearchArticles should return a error result`() = runTest {
        /** Given */
        coEvery { newsService.getArticles("business") } throws error

        /** When */
        val result = topNewsRepository.getSearchArticles("business")

        /** Then */
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).error).isInstanceOf(Exception::class.java)
        assertThat((result).error.message).isEqualTo("exception")
        coVerify(exactly = 1) { newsService.getArticles("business") }
    }

    @Test
    fun getArticlesBySource() = runTest {
        /** Given */
        coEvery { newsService.getArticlesBySources("business") } answers { expected }

        /** When */
        val result = topNewsRepository.getArticlesBySource("business")

        /** Then */
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data.status).isEqualTo("OK")
        assertThat(result.data.articles?.count()).isEqualTo(1)
        assertThat((result).data.articles?.first()?.author).isEqualTo("test")
        coVerify(exactly = 1) { newsService.getArticlesBySources("business") }
    }

    @Test
    fun `getArticlesBySource should return a error resource`() = runTest {
        /** Given */
        coEvery { newsService.getArticlesBySources("business") } throws error

        /** When */
        val result = topNewsRepository.getArticlesBySource("business")

        /** Then */
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).error).isInstanceOf(Exception::class.java)
        assertThat((result).error.message).isEqualTo("exception")
        coVerify(exactly = 1) { newsService.getArticlesBySources("business") }
    }
}