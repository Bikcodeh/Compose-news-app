package com.bikcodeh.newsapp.ui.screen.category

import androidx.activity.viewModels
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.bikcodeh.newsapp.MainActivity
import com.bikcodeh.newsapp.base.BaseUITest
import com.bikcodeh.newsapp.network.FILE_SUCCESS_NEWS_BY_CATEGORY_BUSINESS_RESPONSE
import com.bikcodeh.newsapp.network.FILE_SUCCESS_NEWS_BY_CATEGORY_GENERAL_RESPONSE
import com.bikcodeh.newsapp.network.mockResponse
import com.bikcodeh.newsapp.ui.screen.viewmodel.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okhttp3.mockwebserver.SocketPolicy
import org.junit.Rule
import org.junit.Test
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

@HiltAndroidTest
class CategoryScreenTest : BaseUITest(dispatcher = newsDispatcher) {

    @get:Rule(order = 1)
    val hiltAndroidTest = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun setView() {
        composeTestRule.setContent {
            CategoriesScreen(
                mainViewModel = composeTestRule.activity.viewModels<MainViewModel>().value
            )
        }
    }

    @Test
    fun assertArticlesByCategoryAreProperlyDisplayed() {
        typeRequest = TypeRequest.NoError
        setView()
        composeTestRule.onNodeWithTag(CategoriesTestTags.LAZY_COLUMN_CONTAINER).assertIsDisplayed()
        composeTestRule.onAllNodesWithTag(CategoriesTestTags.CARD_ITEM).assertCountEquals(5)
    }

    @Test
    fun assertDisplayErrorConnectivityScreen() {
        typeRequest = TypeRequest.Connectivity
        setView()
        composeTestRule.onNodeWithTag("ErrorBox").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ErrorMessage").assert(hasText("Connectivity Error"))
    }

    @Test
    fun assertDisplayErrorScreen() {
        typeRequest = TypeRequest.Server
        setView()
        composeTestRule.onNodeWithTag("ErrorBox").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ErrorMessage").assert(hasText("Internal server error: 500"))
    }

    @Test
    fun assertLoadingIsDisplayed() {
        typeRequest = TypeRequest.DelayResponse
        setView()
        composeTestRule.onNodeWithTag("LoadingView").assertIsDisplayed()
    }
}

private val newsDispatcher by lazy {
    object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {

            return when (typeRequest) {
                TypeRequest.Connectivity -> {
                    MockResponse().apply {
                        setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
                    }
                }
                TypeRequest.Server -> {
                    MockResponse().apply {
                        setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                    }
                }
                TypeRequest.DelayResponse -> {
                    mockResponse(
                        FILE_SUCCESS_NEWS_BY_CATEGORY_BUSINESS_RESPONSE,
                        HttpURLConnection.HTTP_OK
                    ).apply {
                        setBodyDelay(3, TimeUnit.SECONDS)
                    }
                }
                TypeRequest.NoError -> {
                    mockResponse(
                        FILE_SUCCESS_NEWS_BY_CATEGORY_BUSINESS_RESPONSE,
                        HttpURLConnection.HTTP_OK
                    )
                }
                TypeRequest.CategoryGeneral -> mockResponse(
                    FILE_SUCCESS_NEWS_BY_CATEGORY_GENERAL_RESPONSE,
                    HttpURLConnection.HTTP_OK
                )
            }
        }
    }
}

private sealed class TypeRequest {
    object Connectivity : TypeRequest()
    object Server : TypeRequest()
    object DelayResponse : TypeRequest()
    object NoError : TypeRequest()
    object CategoryGeneral : TypeRequest()
}

private var typeRequest: TypeRequest = TypeRequest.NoError