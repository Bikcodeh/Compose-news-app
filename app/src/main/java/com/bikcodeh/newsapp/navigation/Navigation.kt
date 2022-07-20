package com.bikcodeh.newsapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bikcodeh.newsapp.ui.screen.detail.DetailScreen
import com.bikcodeh.newsapp.ui.screen.home.bottomNavigation
import com.bikcodeh.newsapp.ui.screen.viewmodel.MainViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = BottomMenuScreen.TopNews.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(
            Screen.Detail.route,
            arguments = listOf(navArgument(Screen.Detail.NAV_ARG_KEY) {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt(Screen.Detail.NAV_ARG_KEY)
            DetailScreen(
                onBackPressed = { navController.popBackStack() },
                mainViewModel,
                index = index
            )
        }

        bottomNavigation(navController = navController, mainViewModel)
    }
}