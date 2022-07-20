package com.bikcodeh.newsapp.ui.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bikcodeh.newsapp.data.model.TopNewsArticle
import com.bikcodeh.newsapp.domain.common.toError
import com.bikcodeh.newsapp.navigation.Screen
import com.bikcodeh.newsapp.ui.component.ErrorScreen
import com.bikcodeh.newsapp.ui.component.LoadingScreen
import com.bikcodeh.newsapp.ui.component.SearchBar
import com.bikcodeh.newsapp.ui.component.TopNewsItem
import com.bikcodeh.newsapp.ui.screen.viewmodel.MainViewModel

@Composable
fun TopNews(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val resultList = mutableListOf<TopNewsArticle>()

    val mainState by mainViewModel.mainState.collectAsState()
    val query by mainViewModel.searchQuery

    if (mainState.isLoading) {
        LoadingScreen()
    }

    mainState.error?.let {
        ErrorScreen(error = it.toError())
    }

    if (mainState.searchedNews.isNotEmpty()) {
        resultList.addAll(mainState.searchedNews)
    } else {
        resultList.addAll(mainState.articles)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = "ColumnArticlesHome"
            }, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            text = query,
            onTextChange = {
                mainViewModel.updateQuery(it)
            },
            onCloseClicked = {
                mainViewModel.clearSearch()
            },
            onSearchClicked = {
                mainViewModel.getSearchArticles(it)
            }
        )
        LazyColumn(modifier = Modifier.testTag("LazyColumnArticlesHome")) {
            items(resultList.count()) { index ->
                TopNewsItem(article = resultList[index], onItemClick = {
                    navController.navigate(Screen.Detail.passNewsIndex(index))
                })
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun TopNewsPreview() {
    TopNews(rememberNavController(), viewModel())
}