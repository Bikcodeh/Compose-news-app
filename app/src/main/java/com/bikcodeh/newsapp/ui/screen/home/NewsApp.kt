package com.bikcodeh.newsapp.ui.screen.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bikcodeh.newsapp.navigation.BottomMenuScreen
import com.bikcodeh.newsapp.navigation.Navigation
import com.bikcodeh.newsapp.ui.component.BottomMenu
import com.bikcodeh.newsapp.ui.screen.category.CategoriesScreen
import com.bikcodeh.newsapp.ui.screen.source.SourceScreen
import com.bikcodeh.newsapp.ui.screen.viewmodel.MainViewModel

@Composable
fun NewsApp(mainViewModel: MainViewModel) {
    val navController = rememberNavController()

    MainScreen(navController, mainViewModel)
}

@Composable
fun MainScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    Scaffold(bottomBar = {
        BottomMenu(navController = navController)
    }) {
        Navigation(navController, paddingValues = it, mainViewModel = mainViewModel)
    }
}

fun NavGraphBuilder.bottomNavigation(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController, mainViewModel)
    }

    composable(BottomMenuScreen.Categories.route) {
        CategoriesScreen(mainViewModel = mainViewModel)
    }

    composable(BottomMenuScreen.Sources.route) {
        SourceScreen(mainViewModel)
    }
}
