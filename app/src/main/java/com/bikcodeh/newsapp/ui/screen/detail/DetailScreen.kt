package com.bikcodeh.newsapp.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bikcodeh.newsapp.R
import com.bikcodeh.newsapp.domain.model.MockData
import com.bikcodeh.newsapp.domain.model.News

@Composable
fun DetailScreen(news: News, scrollState: ScrollState) {
    Column(modifier = Modifier.fillMaxWidth().verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.detail_screen_title),
            fontWeight = FontWeight.SemiBold
        )
        Image(painter = painterResource(id = news.image), contentDescription = "")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            InfoWithIcon(icon = Icons.Default.Edit, info = news.author)
            InfoWithIcon(icon = Icons.Default.DateRange, info = news.publishedAt)
        }
        Text(text = news.title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp))
        Text(text = news.description, modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp))
    }
}

@Composable
fun InfoWithIcon(icon: ImageVector, info: String) {
    Row {
        Icon(
            icon, contentDescription = "Author", modifier = Modifier.padding(end = 8.dp),
            colorResource(id = R.color.purple_500)
        )
        Text(text = info)
    }
}


@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(MockData.topNewsList.first(), rememberScrollState())
}