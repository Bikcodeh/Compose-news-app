package com.bikcodeh.newsapp.ui.screen.home

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
        Navigation(navController, scrollState)
    }
}

fun NavGraphBuilder.bottomNavigation(navController: NavController) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController)
    }

    composable(BottomMenuScreen.Categories.route) {
        CategoriesScreen()
    }

    composable(BottomMenuScreen.Sources.route) {
        SourceScreen()
    }
}
