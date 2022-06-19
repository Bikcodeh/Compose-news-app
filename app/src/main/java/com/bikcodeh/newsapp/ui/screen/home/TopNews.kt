package com.bikcodeh.newsapp.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bikcodeh.newsapp.domain.model.MockData
import com.bikcodeh.newsapp.ui.component.TopNewsItem

@Composable
fun TopNews(navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn() {
            items(MockData.topNewsList) { item ->
                TopNewsItem(news = item, onItemClick = {
                    navController.navigate("DetailScreen/${item.id}")
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopNewsPreview() {
    TopNews(rememberNavController())
}