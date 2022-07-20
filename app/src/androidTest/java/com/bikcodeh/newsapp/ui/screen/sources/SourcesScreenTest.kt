package com.bikcodeh.newsapp.ui.screen.sources

import androidx.activity.viewModels
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.bikcodeh.newsapp.MainActivity
import com.bikcodeh.newsapp.base.BaseUITest
import com.bikcodeh.newsapp.network.FILE_SUCCESS_EMPTY_RESPONSE
import com.bikcodeh.newsapp.network.FILE_SUCCESS_NEWS_BY_SOURCE_RESPONSE
import com.bikcodeh.newsapp.network.FILE_SUCCESS_NEWS_BY_SOURCE_TALK_SPORT_RESPONSE
import com.bikcodeh.newsapp.network.mockResponse
import com.bikcodeh.newsapp.ui.screen.source.SourceScreen
import com.bikcodeh.newsapp.ui.screen.source.SourcesTestTags
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

@ExperimentalTestApi
@HiltAndroidTest
class SourcesScreenTest : BaseUITest(dispatcher = newsDispatcher) {

    @get:Rule(order = 1)
    val hiltAndroidTest = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun setView() {
        composeTestRule.setContent {
            SourceScreen(mainViewModel = composeTestRule.activity.viewModels<MainViewModel>().value)
        }
    }

    @Test
    fun assertSourcesScreenProperlyDisplayed() {
        setView()
        composeTestRule.onNodeWithTag(SourcesTestTags.LAZY_COLUMN_CONTAINER).assertIsDisplayed()
    }

    @Test
    fun assertSourceItemsAreProperlyDisplayed() {
        setView()
        composeTestRule.onNodeWithTag(SourcesTestTags.LAZY_COLUMN_CONTAINER)
            .assert(hasScrollAction())
        composeTestRule.onAllNodesWithTag(SourcesTestTags.CARD_CONTAINER).onFirst()
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithTag(SourcesTestTags.SOURCE_TITLE)[0].assert(hasText("Celebrating Pride Month at Disneyland Paris"))
        composeTestRule.onAllNodesWithTag(SourcesTestTags.SOURCE_DESCRIPTION)[0].assert(hasText("As we continue celebrating Pride Month, we’re honored to recognize and elevate diverse voices and perspectives across the LGBTQIA+ community, including at Disneyland Paris, where we recently welcomed the return of Disneyland Paris Pride at Walt Disney Studios…"))
        composeTestRule.onNodeWithTag(SourcesTestTags.LAZY_COLUMN_CONTAINER).performScrollToIndex(5)
        composeTestRule.onNodeWithTag(SourcesTestTags.LAZY_COLUMN_CONTAINER).onChildAt(5)
            .assertExists()
    }

    @Test
    fun assertSourceByADifferentSourceItemsAreProperlyDisplayed() {
        typeRequest = TypeRequest.NoError
        setView()
        composeTestRule.onNodeWithTag(SourcesTestTags.TOB_BAR).performClick()
        composeTestRule.onAllNodesWithTag(SourcesTestTags.DROP_DOWN_MENU_ITEM)[1].performClick()
        typeRequest = TypeRequest.NoErrorTalkSport
        composeTestRule.onAllNodesWithTag(SourcesTestTags.CARD_CONTAINER).onFirst()
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithTag(SourcesTestTags.SOURCE_TITLE)[0].assert(hasText("Lewis Hamilton says he feels 'smaller' and reveals other health concerns due to Mercedes car that is 'so bad' as Formula 1 star opens up ahead of Canadian Grand Prix"))
        composeTestRule.onAllNodesWithTag(SourcesTestTags.SOURCE_DESCRIPTION)[0].assert(hasText("Lewis Hamilton says he feels 'smaller' and reveals other health concerns due to Mercedes car that is 'so bad' as Formula 1 star opens up ahead of Canadian Grand Prixtalksport.com"))
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
                        FILE_SUCCESS_NEWS_BY_SOURCE_RESPONSE,
                        HttpURLConnection.HTTP_OK
                    ).apply {
                        setBodyDelay(3, TimeUnit.SECONDS)
                    }
                }
                TypeRequest.Empty -> {
                    mockResponse(FILE_SUCCESS_EMPTY_RESPONSE, HttpURLConnection.HTTP_OK)
                }
                TypeRequest.NoError -> {
                    mockResponse(
                        FILE_SUCCESS_NEWS_BY_SOURCE_RESPONSE,
                        HttpURLConnection.HTTP_OK
                    )
                }
                TypeRequest.NoErrorTalkSport -> {
                    mockResponse(
                        FILE_SUCCESS_NEWS_BY_SOURCE_TALK_SPORT_RESPONSE,
                        HttpURLConnection.HTTP_OK
                    )
                }
            }
        }
    }
}

private sealed class TypeRequest {
    object Connectivity : TypeRequest()
    object Server : TypeRequest()
    object DelayResponse : TypeRequest()
    object NoError : TypeRequest()
    object NoErrorTalkSport : TypeRequest()
    object Empty : TypeRequest()
}

private var typeRequest: TypeRequest = TypeRequest.NoError