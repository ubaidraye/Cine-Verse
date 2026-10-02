package com.raye.tmdbapp.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raye.tmdbapp.presentation.detail.DetailScreen
import com.raye.tmdbapp.presentation.home.HomeScreen
import com.raye.tmdbapp.presentation.search.SearchScreen
import com.raye.tmdbapp.presentation.watchlist.WatchListScreen

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavigationItems = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Watchlist
    )

    val onMovieClick: (Long) -> Unit = { movieId ->
        navController.navigate(Screen.Detail.createRoute(movieId))
    }

    Scaffold(
        bottomBar = {
            NavigationBar(windowInsets = WindowInsets(0)) {
                bottomNavigationItems.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(onMovieClick = onMovieClick)
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(
                    navArgument("movieId") { type = NavType.LongType }
                )
            ) {
                DetailScreen(onBackClick = { navController.popBackStack() })
            }

            composable(
                route = Screen.Search.route
            ) {
                SearchScreen(onMovieClick = onMovieClick)
            }

            composable(
                route = Screen.Watchlist.route
            ) {
                WatchListScreen(onMovieClick = onMovieClick)
            }
        }
    }
}