package com.bikcodeh.newsapp.navigation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bikcodeh.newsapp.data.model.TopNewsArticle
import com.bikcodeh.newsapp.ui.screen.detail.DetailScreen
import com.bikcodeh.newsapp.ui.screen.home.bottomNavigation
import com.bikcodeh.newsapp.ui.screen.viewmodel.MainViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel
) {
    val mainState by mainViewModel.mainState.collectAsState()

    val articles = mutableListOf<TopNewsArticle>()
    articles.addAll(mainState.articles)

    articles.let {
        NavHost(
            navController = navController,
            startDestination = BottomMenuScreen.TopNews.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            bottomNavigation(navController = navController, articles, mainViewModel)

            composable(
                Screen.Detail.route,
                arguments = listOf(navArgument(Screen.Detail.NAV_ARG_KEY) {
                    type = NavType.IntType
                })
            ) { navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt(Screen.Detail.NAV_ARG_KEY)
                if (mainViewModel.searchQuery.value.isNotEmpty()) {
                    articles.clear()
                    articles.addAll(mainState.searchedNews)
                } else {
                    articles.clear()
                    articles.addAll(mainState.articles)
                }
                index?.let {
                    DetailScreen(articles[index], scrollState, navController)
                }
            }
        }
    }
}