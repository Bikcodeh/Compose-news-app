package com.bikcodeh.newsapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikcodeh.newsapp.domain.model.MockData
import com.bikcodeh.newsapp.domain.model.News

@Composable
fun TopNewsItem(news: News) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = news.image),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 6.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = news.publishedAt,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = news.title, color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview
@Composable
fun TopNewsItemPreview() {
    TopNewsItem(news = MockData.topNewsList[1])
}