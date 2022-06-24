package com.bikcodeh.newsapp.ui.screen.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikcodeh.newsapp.R
import com.bikcodeh.newsapp.data.model.TopNewsArticle
import com.bikcodeh.newsapp.domain.common.toError
import com.bikcodeh.newsapp.ui.util.Util
import com.bikcodeh.newsapp.ui.util.Util.getTimeAgo
import com.bikcodeh.newsapp.domain.model.getAllArticleCategory
import com.bikcodeh.newsapp.ui.component.ErrorScreen
import com.bikcodeh.newsapp.ui.component.LoadingScreen
import com.bikcodeh.newsapp.ui.screen.viewmodel.MainViewModel
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun CategoriesScreen(onFetchCategory: (String) -> Unit, mainViewModel: MainViewModel) {
    val tabItems = getAllArticleCategory()
    val mainState by mainViewModel.mainState.collectAsState()

    LaunchedEffect(key1 = mainState.selectedCategory) {
        mainViewModel.getArticlesByCategory("business")
    }

    if (mainState.isLoading) {
        LoadingScreen()
    }

    mainState.error?.let {
        ErrorScreen(error = it.toError())
    }

    Column() {
        LazyRow() {
            items(tabItems.size) {
                val category = tabItems[it]
                CategoryTab(
                    category = category.categoryName,
                    onFetchCategory = onFetchCategory,
                    isSelected = mainState.selectedCategory == category
                )
            }
        }
        ArticleContent(articles = mainState.articlesByCategory)
    }
}

@Composable
fun CategoryTab(
    category: String,
    isSelected: Boolean = false,
    onFetchCategory: (String) -> Unit
) {
    val background =
        if (isSelected) colorResource(id = R.color.purple_200) else colorResource(id = R.color.purple_700)
    Surface(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 16.dp)
            .clickable {
                onFetchCategory(category)
            },
        shape = MaterialTheme.shapes.small,
        color = background
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ArticleContent(articles: List<TopNewsArticle>, modifier: Modifier = Modifier) {
    LazyColumn() {
        items(articles) { article ->
            Card(
                modifier.padding(8.dp),
                border = BorderStroke(2.dp, color = colorResource(id = R.color.purple_500))
            ) {
                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    CoilImage(
                        imageModel = article.urlToImage,
                        modifier = Modifier.size(100.dp),
                        placeHolder = painterResource(id = R.drawable.ic_broken_image),
                        error = painterResource(id = R.drawable.ic_broken_image)
                    )
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = article.title ?: stringResource(id = R.string.not_available), fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = article.author ?: stringResource(id = R.string.not_available))
                            article.publishedAt?.let {
                                Text(text = Util.stringToDate(article.publishedAt).getTimeAgo())
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ArticleContentPreview() {
    ArticleContent(
        articles = listOf(
            TopNewsArticle(
                author = "Namita Singh",
                title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
                description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
                publishedAt = "2021-11-04T04:42:40Z"
            )
        )
    )
}