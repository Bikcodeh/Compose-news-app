package com.bikcodeh.newsapp.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bikcodeh.newsapp.R
import com.bikcodeh.newsapp.navigation.BottomMenuScreen
import com.bikcodeh.newsapp.navigation.Screen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomMenu(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var bottomBarState by rememberSaveable { (mutableStateOf(true)) }

    bottomBarState = when (navController.currentBackStackEntry?.destination?.route) {
        Screen.Detail.route -> false
        else -> true
    }

    val menuItems = listOf(
        BottomMenuScreen.TopNews,
        BottomMenuScreen.Categories,
        BottomMenuScreen.Sources,
    )
    AnimatedVisibility(
        visible = bottomBarState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation(contentColor = colorResource(id = R.color.white)) {
                menuItems.forEach {
                    BottomNavigationItem(label = {
                        Text(text = it.title)
                    }, selected = currentRoute == it.route,
                        onClick = {
                            navController.navigate(it.route) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }, alwaysShowLabel = true,
                        unselectedContentColor = Color.Gray,
                        selectedContentColor = Color.White,
                        icon = { Icon(imageVector = it.icon, contentDescription = it.title) }
                    )
                }
            }
        }
    )
}