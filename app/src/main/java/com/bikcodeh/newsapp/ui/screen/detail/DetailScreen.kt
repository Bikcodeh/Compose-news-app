package com.bikcodeh.newsapp.ui.screen.detail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bikcodeh.newsapp.R
import com.bikcodeh.newsapp.ui.screen.viewmodel.MainViewModel

@Composable
fun DetailScreen(
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
            DetailContent(article = article)
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
fun InfoWithIcon(modifier: Modifier = Modifier, icon: ImageVector, info: String, testTag: String) {
    Row(modifier = modifier) {
        Icon(
            icon,
            contentDescription = stringResource(id = R.string.author),
            modifier = Modifier
                .padding(end = 8.dp)
                .testTag(testTag),
            colorResource(id = R.color.purple_500)
        )
        Text(text = info, modifier = Modifier.testTag("TextInfoIcon"))
    }
}