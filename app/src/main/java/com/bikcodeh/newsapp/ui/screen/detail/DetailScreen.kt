package com.bikcodeh.newsapp.ui.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bikcodeh.newsapp.R
import com.bikcodeh.newsapp.domain.model.MockData
import com.bikcodeh.newsapp.domain.model.News

@Composable
fun DetailScreen(navController: NavController, news: News) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.detail_screen_title),
            fontWeight = FontWeight.SemiBold
        )
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Back + ${news.title}")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(rememberNavController(), MockData.topNewsList.first())
}