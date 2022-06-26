package com.bikcodeh.newsapp.ui.component

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test


class SearchBarTest {

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private val text = mutableStateOf("")

    @Test
    fun assertSearchBarIsProperlyDisplayed() {
        composeTestRule.setContent {
            SearchBar(text = "", onTextChange = {}, onSearchClicked = {}, onCloseClicked = {})
        }
        composeTestRule.onNodeWithTag("SearchBarNews").assertIsDisplayed()
    }

    @Test
    fun openSearchBar_addInputText_assertInputText() {

        composeTestRule.setContent {
            SearchBar(
                text = text.value,
                onTextChange = { text.value = it },
                onSearchClicked = {},
                onCloseClicked = {}
            )
        }
        composeTestRule.onNodeWithTag("SearchBarNews").assertIsDisplayed()
        composeTestRule.onNodeWithTag("SearchBarTextField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("SearchBarTextField").performTextInput("Bikcode")
        composeTestRule.onNodeWithTag("SearchBarTextField").assert(hasText("Bikcode"))
    }

    @Test
    fun openSearchBar_addInputText_pressCloseButtonOnce_assertEmptyInputText() {

        composeTestRule.setContent {
            SearchBar(
                text = text.value,
                onTextChange = { text.value = it },
                onSearchClicked = {},
                onCloseClicked = {
                    text.value = ""
                }
            )
        }
        composeTestRule.onNodeWithTag("SearchBarNews").assertIsDisplayed()
        composeTestRule.onNodeWithTag("SearchBarTextField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("SearchBarTextField").performTextInput("Bikcode")
        composeTestRule.onNodeWithTag("CloseIconSearchBar").performClick()
        composeTestRule.onNodeWithTag("SearchBarTextField").assert(hasText(""))
    }

}