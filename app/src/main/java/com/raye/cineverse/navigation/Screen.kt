package com.raye.cineverse.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : Screen("home", "Home", Icons.Default.Home)
    data object Search : Screen("search", "Search", Icons.Default.Search)
    data object Detail : Screen("detail/{movieId}", "Detail", Icons.Default.Details) {
        fun createRoute(movieId: Long): String = "detail/$movieId"
    }

    data object Watchlist : Screen("watchlist", "Watchlist", Icons.Default.Bookmark)
}