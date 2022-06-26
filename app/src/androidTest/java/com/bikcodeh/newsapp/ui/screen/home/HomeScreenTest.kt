package com.bikcodeh.newsapp.ui.screen.home

import androidx.activity.viewModels
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.bikcodeh.newsapp.MainActivity
import com.bikcodeh.newsapp.base.BaseUITest
import com.bikcodeh.newsapp.network.FILE_SUCCESS_EMPTY_RESPONSE
import com.bikcodeh.newsapp.network.FILE_SUCCESS_NEWS_RESPONSE
import com.bikcodeh.newsapp.network.mockResponse
import com.bikcodeh.newsapp.ui.screen.viewmodel.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.internal.wait
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okhttp3.mockwebserver.SocketPolicy
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.HttpURLConnection.HTTP_UNAVAILABLE
import java.util.concurrent.TimeUnit

@ExperimentalTestApi
@HiltAndroidTest
class HomeScreenTest : BaseUITest(dispatcher = newsDispatcher) {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltTestRule.inject()
    }

    @Test
    fun assertItemsAreDisplayed() {
        typeRequest = TypeRequest.NoError
        setView()
        composeTestRule.onNodeWithTag("LazyColumnArticlesHome").assertIsDisplayed()
        composeTestRule.onAllNodesWithContentDescription("NewItem")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithContentDescription("TitleNew")[0]
            .assertTextContains("Special Episode: Roe v. Wade Is Overturned - The New York Times")
        composeTestRule.onAllNodesWithContentDescription("PublishedNew")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithContentDescription("PublishedNew")[0]
            .assertTextContains("1 day ago")
    }

    @Test
    fun assertTotalItemsAreDisplayed() {
        typeRequest = TypeRequest.NoError
        setView()
        composeTestRule.onNodeWithTag("LazyColumnArticlesHome").assertIsDisplayed()
        composeTestRule.onNodeWithTag("LazyColumnArticlesHome").assert(hasScrollAction())
        composeTestRule.onNodeWithTag("LazyColumnArticlesHome").performScrollToIndex(19)
    }

    @Test
    fun assertSearchBarIsDisplayed() {
        setView()
        composeTestRule.onNodeWithTag("SearchBarNews").assertIsDisplayed()
        composeTestRule.onNodeWithTag("SearchBarTextField").assert(hasText(""))
    }

    @Test
    fun assertNavigateToDetail() {
        setView()
        composeTestRule.onNodeWithTag("LazyColumnArticlesHome").onChildAt(1).performClick()
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

    private fun setView() {
        composeTestRule.setContent {
            NewsApp(mainViewModel = composeTestRule.activity.viewModels<MainViewModel>().value)
        }
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
                    mockResponse(FILE_SUCCESS_NEWS_RESPONSE, HTTP_OK).apply {
                     setBodyDelay(3, TimeUnit.SECONDS)
                    }
                }
                TypeRequest.Empty -> {
                    mockResponse(FILE_SUCCESS_EMPTY_RESPONSE, HTTP_OK)
                }
                TypeRequest.NoError -> {
                    mockResponse(FILE_SUCCESS_NEWS_RESPONSE, HTTP_OK)
                }
            }
        }
    }
}

private sealed class TypeRequest {
    object Connectivity : TypeRequest()
    object Server : TypeRequest()
    object DelayResponse: TypeRequest()
    object NoError : TypeRequest()
    object Empty : TypeRequest()
}

private var typeRequest: TypeRequest = TypeRequest.NoError