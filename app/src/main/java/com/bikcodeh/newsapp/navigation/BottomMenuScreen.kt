package com.bikcodeh.newsapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomMenuScreen(
    val route: String,
    val icon: ImageVector,
    val title: String
) {
    object TopNews : BottomMenuScreen("TopNewsScreen", icon = Icons.Outlined.Home, "Top News")
    object Categories :
        BottomMenuScreen("CategoriesScreen", icon = Icons.Outlined.Category, "Categories")

    object Sources : BottomMenuScreen("SourcesScreen", icon = Icons.Outlined.Home, "Sources")
}
