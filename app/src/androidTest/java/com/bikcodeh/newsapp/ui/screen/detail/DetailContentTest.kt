package com.bikcodeh.newsapp.ui.screen.detail

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.bikcodeh.newsapp.data.model.TopNewsArticle
import org.junit.Rule
import org.junit.Test

class DetailContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setView() {
        composeTestRule.setContent {
            DetailContent(
                article = TopNewsArticle(
                    author = "CBSBoston.com Staff 1",
                    title = "TestTitle",
                    description = "Principal 1 Patricia Lampron and another employee were assaulted at Henderson Upper Campus during dismissal on Wednesday.",
                    publishedAt = "2021-11-04T01:55:00Z",
                    urlToImage = "https://mms.businesswire.com/media/20220624005500/en/538768/21/BES_Mark.jpg"
                )
            )
        }
    }

    @Test
    fun assertInfoIconsProperlyDisplayed() {
        setView()
        composeTestRule.onNodeWithTag("dateInfoIcon").assertIsDisplayed()
        composeTestRule.onNodeWithTag("authorInfoIcon").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("TextInfoIcon")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("TextInfoIcon")[1].assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("TextInfoIcon")[0].assert(hasText("CBSBoston.com Staff 1"))
        composeTestRule.onAllNodesWithTag("TextInfoIcon")[1].assert(hasText("1 year ago"))
    }

    @Test
    fun assertTitleAndDescriptionProperlyDisplayed() {
        setView()
        composeTestRule.onNodeWithTag("TitleDetail").assertIsDisplayed()
        composeTestRule.onNodeWithTag("TitleDetail").assertIsDisplayed()
        composeTestRule.onNodeWithTag("TitleDetail").assert(hasText("TestTitle"))
        composeTestRule.onNodeWithTag("DescriptionDetail")
            .assert(hasText("Principal 1 Patricia Lampron and another employee were assaulted at Henderson Upper Campus during dismissal on Wednesday."))
    }
}