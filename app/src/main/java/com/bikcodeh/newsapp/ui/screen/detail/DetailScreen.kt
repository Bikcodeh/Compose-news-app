package com.bikcodeh.newsapp.ui.screen.detail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bikcodeh.newsapp.R
import com.bikcodeh.newsapp.ui.screen.viewmodel.MainViewModel
import com.bikcodeh.newsapp.ui.util.Util
import com.bikcodeh.newsapp.ui.util.Util.getTimeAgo
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun DetailScreen(
    scrollState: ScrollState,
    onBackPressed: () -> Unit,
    mainViewModel: MainViewModel,
    index: Int?
) {
    LaunchedEffect(key1 = true) {
        mainViewModel.getSelectedArticle(index)
    }

    val article by mainViewModel.selectedNews.collectAsState()

    Scaffold(
        topBar = {
            DetailTopBar(onBackPressed = { onBackPressed() })
        }
    ) {
        article?.let { article ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally
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
                        info = article.author ?: stringResource(id = R.string.not_available)
                    )
                    InfoWithIcon(
                        icon = Icons.Default.DateRange,
                        info = Util.stringToDate(article.publishedAt!!).getTimeAgo()
                    )
                }
                Text(
                    text = article.title ?: stringResource(id = R.string.not_available),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Text(
                    text = article.description ?: stringResource(id = R.string.not_available),
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                )
            }
        }
    }
}

@Composable
fun DetailTopBar(onBackPressed: () -> Unit = {}) {
    TopAppBar(title = { Text(text = stringResource(id = R.string.detail_screen_title)) },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        })
}

@Composable
fun InfoWithIcon(icon: ImageVector, info: String) {
    Row {
        Icon(
            icon,
            contentDescription = stringResource(id = R.string.author),
            modifier = Modifier.padding(end = 8.dp),
            colorResource(id = R.color.purple_500)
        )
        Text(text = info)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        rememberScrollState(),
        onBackPressed = {},
        viewModel(),
        1
    )
}