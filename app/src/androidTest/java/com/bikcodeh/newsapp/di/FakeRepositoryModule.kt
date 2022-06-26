package com.bikcodeh.newsapp.di

/*
import com.bikcodeh.newsapp.data.model.TopNewsArticle
import com.bikcodeh.newsapp.data.model.TopNewsResponse
import com.bikcodeh.newsapp.domain.common.Result
import com.bikcodeh.newsapp.domain.repository.TopNewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)

@Module
object FakeRepositoryModule {

    @Singleton
    @Provides
    fun providesFakeRepository() = object : TopNewsRepository {

        override suspend fun getArticles(): Result<TopNewsResponse> {
            return Result.Success(
                TopNewsResponse(
                    articles = listOf(
                        TopNewsArticle(
                            author = "CBSBoston.com Staff 1",
                            title = "TestTitle",
                            description = "Principal 1 Patricia Lampron and another employee were assaulted at Henderson Upper Campus during dismissal on Wednesday.",
                            publishedAt = "2021-11-04T01:55:00Z",
                            urlToImage = "https://mms.businesswire.com/media/20220624005500/en/538768/21/BES_Mark.jpg"
                        ),
                        TopNewsArticle(
                            author = "CBSBoston.com Staff 2",
                            title = "TestTitle",
                            description = "Principal 2 Patricia Lampron and another employee were assaulted at Henderson Upper Campus during dismissal on Wednesday.",
                            publishedAt = "2021-11-04T01:55:00Z",
                            urlToImage = "https://mms.businesswire.com/media/20220624005500/en/538768/21/BES_Mark.jpg"
                        ),
                        TopNewsArticle(
                            author = "CBSBoston.com Staff 3",
                            title = "TestTitle 3",
                            description = "Principal 3 Patricia Lampron and another employee were assaulted at Henderson Upper Campus during dismissal on Wednesday.",
                            publishedAt = "2021-11-04T01:55:00Z",
                            urlToImage = "https://mms.businesswire.com/media/20220624005500/en/538768/21/BES_Mark.jpg"
                        ),
                        TopNewsArticle(
                            author = "CBSBoston.com Staff 4",
                            title = "TestTitle 4",
                            description = "Principal 4 Patricia Lampron and another employee were assaulted at Henderson Upper Campus during dismissal on Wednesday.",
                            publishedAt = "2021-11-04T01:55:00Z",
                            urlToImage = "https://mms.businesswire.com/media/20220624005500/en/538768/21/BES_Mark.jpg"
                        ),
                        TopNewsArticle(
                            author = "CBSBoston.com Staff 5",
                            title = "TestTitle 5",
                            description = "Principal 5 Patricia Lampron and another employee were assaulted at Henderson Upper Campus during dismissal on Wednesday.",
                            publishedAt = "2021-11-04T01:55:00Z",
                            urlToImage = "https://mms.businesswire.com/media/20220624005500/en/538768/21/BES_Mark.jpg"
                        )
                    )
                )
            )
        }

        override suspend fun getArticlesByCategory(category: String): Result<TopNewsResponse> {
            return Result.Success(
                TopNewsResponse(
                    articles = listOf(
                        TopNewsArticle(
                            author = "CBSBoston.com Staff",
                            title = "Principal Beaten Unconscious At Dorchester School; Classes Canceled Thursday - CBS Boston",
                            description = "Principal Patricia Lampron and another employee were assaulted at Henderson Upper Campus during dismissal on Wednesday.",
                            publishedAt = "2021-11-04T01:55:00Z",
                            urlToImage = "https://mms.businesswire.com/media/20220624005500/en/538768/21/BES_Mark.jpg"
                        )
                    )
                )
            )
        }

        override suspend fun getSearchArticles(query: String): Result<TopNewsResponse> {
            return Result.Success(
                TopNewsResponse(
                    articles = listOf(
                        TopNewsArticle(
                            author = "CBSBoston.com Staff",
                            title = "TestTitle",
                            description = "Principal Patricia Lampron and another employee were assaulted at Henderson Upper Campus during dismissal on Wednesday.",
                            publishedAt = "2021-11-04T01:55:00Z",
                            urlToImage = "https://mms.businesswire.com/media/20220624005500/en/538768/21/BES_Mark.jpg"
                        )
                    )
                )
            )
        }

        override suspend fun getArticlesBySource(source: String): Result<TopNewsResponse> {
            return Result.Success(
                TopNewsResponse(
                    articles = listOf(
                        TopNewsArticle(
                            author = "CBSBoston.com Staff",
                            title = "Principal Beaten Unconscious At Dorchester School; Classes Canceled Thursday - CBS Boston",
                            description = "Principal Patricia Lampron and another employee were assaulted at Henderson Upper Campus during dismissal on Wednesday.",
                            publishedAt = "2021-11-04T01:55:00Z",
                            urlToImage = "https://mms.businesswire.com/media/20220624005500/en/538768/21/BES_Mark.jpg"
                        )
                    )
                )
            )
        }
    }
}

 */