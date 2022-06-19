package com.bikcodeh.newsapp.navigation

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bikcodeh.newsapp.domain.model.MockData
import com.bikcodeh.newsapp.ui.screen.detail.DetailScreen
import com.bikcodeh.newsapp.ui.screen.home.TopNews
import com.bikcodeh.newsapp.ui.screen.home.bottomNavigation

@Composable
fun Navigation(navController: NavHostController, scrollState: ScrollState) {
    NavHost(navController = navController, startDestination = "TopNewsScreen") {
        bottomNavigation(navController = navController)
        composable(
            "DetailScreen/{newsId}",
            arguments = listOf(navArgument("newsId") {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("newsId")
            val new = MockData.getNew(id)
            DetailScreen(new, scrollState, navController)
        }
    }
}