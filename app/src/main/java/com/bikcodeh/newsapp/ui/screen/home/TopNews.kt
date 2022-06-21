package com.bikcodeh.newsapp.ui.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bikcodeh.newsapp.data.model.TopNewsArticle
import com.bikcodeh.newsapp.data.remote.Api
import com.bikcodeh.newsapp.navigation.Screen
import com.bikcodeh.newsapp.ui.component.SearchBar
import com.bikcodeh.newsapp.ui.component.TopNewsItem

@Composable
fun TopNews(
    navController: NavController,
    articles: List<TopNewsArticle>,
    query: MutableState<String>,
    newsManager: NewsManager
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        SearchBar(query = query, newsManager = newsManager)
        val searchedText = query.value
        val resultList = mutableListOf<TopNewsArticle>()
        if (searchedText.isNotEmpty()) {
            resultList.addAll(newsManager.searchedNews.value.articles ?: articles)
        } else {
            resultList.addAll(articles)
        }

        LazyColumn() {
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
    TopNews(rememberNavController(), emptyList(), mutableStateOf(""), NewsManager(Api.retrofitService))
}