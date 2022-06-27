package com.bikcodeh.newsapp.ui.screen.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikcodeh.newsapp.R
import com.bikcodeh.newsapp.data.model.TopNewsArticle
import com.bikcodeh.newsapp.ui.util.Util
import com.bikcodeh.newsapp.ui.util.Util.getTimeAgo
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun DetailContent(article: TopNewsArticle) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilImage(
            imageModel = article.urlToImage,
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            error = painterResource(id = R.drawable.ic_broken_image),
            placeHolder = painterResource(id = R.drawable.ic_broken_image)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            InfoWithIcon(
                icon = Icons.Default.Edit,
                info = article.author ?: stringResource(id = R.string.not_available),
                testTag = "authorInfoIcon"
            )
            InfoWithIcon(
                icon = Icons.Default.DateRange,
                info = Util.stringToDate(article.publishedAt!!).getTimeAgo(),
                testTag = "dateInfoIcon"
            )
        }
        Text(
            text = article.title ?: stringResource(id = R.string.not_available),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .testTag("TitleDetail")
        )
        Text(
            text = article.description ?: stringResource(id = R.string.not_available),
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .testTag("DescriptionDetail")
        )
    }
}

@Composable
@Preview(showBackground = true)
fun DetailContentPreview() {
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