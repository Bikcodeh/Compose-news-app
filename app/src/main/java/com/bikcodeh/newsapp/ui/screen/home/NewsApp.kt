package com.bikcodeh.newsapp.ui.screen.home

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bikcodeh.newsapp.data.model.TopNewsArticle
import com.bikcodeh.newsapp.navigation.BottomMenuScreen
import com.bikcodeh.newsapp.navigation.Navigation
import com.bikcodeh.newsapp.ui.component.BottomMenu
import com.bikcodeh.newsapp.ui.screen.category.CategoriesScreen
import com.bikcodeh.newsapp.ui.screen.source.SourceScreen

@Composable
fun NewsApp() {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()

    MainScreen(navController, scrollState)
}

@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState) {
    Scaffold(bottomBar = {
        BottomMenu(navController = navController)
    }) {
        Navigation(navController, scrollState, paddingValues = it)
    }
}

fun NavGraphBuilder.bottomNavigation(
    navController: NavController,
    articles: List<TopNewsArticle>,
    newsManager: NewsManager
) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController, articles, newsManager.query, newsManager)
    }

    composable(BottomMenuScreen.Categories.route) {
        newsManager.onSelectedCategoryChanged("business")
        newsManager.getArticlesByCategory("business")

        CategoriesScreen(onFetchCategory = {
            newsManager.onSelectedCategoryChanged(it)
            newsManager.getArticlesByCategory(it)
        }, newsManager)
    }

    composable(BottomMenuScreen.Sources.route) {
        SourceScreen(newsManager = newsManager)
    }
}
