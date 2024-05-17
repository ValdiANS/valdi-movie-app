package com.myapplication.valdimovieapp.ui.route

sealed class Screen(val route: String) {
    object Home : Screen("home")

    object Search : Screen("search")

    object Favorite : Screen("favorite")

    object About : Screen("about")

    object DetailMovie : Screen("movie/{movieId}") {
        fun createRoute(movieId: String) = "movie/$movieId"
    }
}
