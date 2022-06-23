package com.bikcodeh.newsapp.ui.screen.source

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikcodeh.newsapp.R
import com.bikcodeh.newsapp.data.model.TopNewsArticle
import com.bikcodeh.newsapp.domain.common.toError
import com.bikcodeh.newsapp.ui.component.ErrorScreen
import com.bikcodeh.newsapp.ui.component.LoadingScreen
import com.bikcodeh.newsapp.ui.screen.viewmodel.MainViewModel

@Composable
fun SourceScreen(mainViewModel: MainViewModel) {

    val items = listOf(
        "TechCrunch" to "techcrunch",
        "TalkSport" to "talksport",
        "Business Insider" to "business-insider",
        "Reuters" to "reuters",
        "Politico" to "politico",
        "TheVerge" to "the-verge"
    )

    val sourceName by mainViewModel.sourceName
    val mainState by mainViewModel.mainState.collectAsState()

    LaunchedEffect(key1 = sourceName) {
        mainViewModel.getArticlesBySource()
    }

    if (mainState.isLoading) {
        LoadingScreen()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "$sourceName Source") },
                actions = {
                    var menuExpanded by remember { mutableStateOf(false) }
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }
                    MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            items.forEach {
                                DropdownMenuItem(onClick = {
                                    mainViewModel.updateSource(it.second)
                                    menuExpanded = false
                                }) {
                                    Text(text = it.first)
                                }
                            }
                        }
                    }
                })
        }
    ) {
        mainState.error?.let {
            ErrorScreen(error = it.toError())
        } ?: SourceContent(articles = mainState.articlesBySource)
    }
}

@Composable
fun SourceContent(articles: List<TopNewsArticle>) {

    val uriHandler = LocalUriHandler.current

    LazyColumn(modifier = Modifier) {
        items(articles) { article ->
            /** configuration to open a link with a text*/
            val annotatedString = buildAnnotatedString {
                pushStringAnnotation(
                    tag = "URL",
                    annotation = article.url ?: "newsapi.org"
                )
                withStyle(
                    style = SpanStyle(
                        color = colorResource(id = R.color.purple_500),
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(stringResource(id = R.string.read_article))
                }
            }
            Card(
                backgroundColor = colorResource(id = R.color.purple_700),
                elevation = 6.dp,
                modifier = Modifier
                    .height(200.dp)
                    .padding(vertical = 4.dp, horizontal = 16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    Text(
                        text = article.title ?: stringResource(id = R.string.not_available),
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = article.description ?: stringResource(id = R.string.not_available),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Card(backgroundColor = Color.White, elevation = 6.dp) {
                        /** Clickable text with uri handler */
                        ClickableText(
                            text = annotatedString,
                            modifier = Modifier.padding(8.dp), onClick = {
                                annotatedString.getStringAnnotations(it, it).firstOrNull()
                                    ?.let { result ->
                                        if (result.tag == "URL") {
                                            uriHandler.openUri(result.item)
                                        }
                                    }
                            })
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SourceContentPreview() {
    SourceContent(
        articles = listOf(
            TopNewsArticle(
                author = "CBSBoston.com Staff",
                title = "Principal Beaten Unconscious At Dorchester School; Classes Canceled Thursday - CBS Boston",
                description = "Principal Patricia Lampron and another employee were assaulted at Henderson Upper Campus during dismissal on Wednesday.",
                publishedAt = "2021-11-04T01:55:00Z"
            )
        )
    )
}