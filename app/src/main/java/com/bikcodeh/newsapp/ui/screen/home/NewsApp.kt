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
import com.bikcodeh.newsapp.ui.screen.viewmodel.MainViewModel

@Composable
fun NewsApp(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()

    MainScreen(navController, scrollState, mainViewModel)
}

@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState, mainViewModel: MainViewModel) {
    Scaffold(bottomBar = {
        BottomMenu(navController = navController)
    }) {
        Navigation(navController, scrollState, paddingValues = it, mainViewModel = mainViewModel)
    }
}

fun NavGraphBuilder.bottomNavigation(
    navController: NavController,
    articles: List<TopNewsArticle>,
    mainViewModel: MainViewModel
) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController, articles, mainViewModel)
    }

    composable(BottomMenuScreen.Categories.route) {
        mainViewModel.onSelectedCategoryChanged("business")
        mainViewModel.getArticlesByCategory("business")

        CategoriesScreen(mainViewModel = mainViewModel, onFetchCategory = {
            mainViewModel.onSelectedCategoryChanged(it)
            mainViewModel.getArticlesByCategory(it)
        })
    }

    composable(BottomMenuScreen.Sources.route) {
        SourceScreen(mainViewModel)
    }
}
