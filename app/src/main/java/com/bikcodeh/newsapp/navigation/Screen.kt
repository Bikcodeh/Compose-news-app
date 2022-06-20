package com.bikcodeh.newsapp.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Sources : Screen("sources_screen")
    object Categories : Screen("categories_screen")
    object Detail : Screen("detail_screen/{index}") {
        const val NAV_ARG_KEY = "index"
        fun passNewsIndex(index: Int): String {
            return "detail_screen/${index}"
        }
    }
}
