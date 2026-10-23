package com.raye.cineverse.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raye.cineverse.presentation.home.components.ErrorContent
import com.raye.cineverse.presentation.home.components.HomeContent


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(
    onMovieClick: (movieId: Long) -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    val pullToRefreshState = rememberPullToRefreshState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(title = { Text("Cine Verse") }, windowInsets = WindowInsets(0))
        PullToRefreshBox(
            state = pullToRefreshState,
            isRefreshing = isRefreshing,
            onRefresh = viewModel::refreshHome,
            modifier = Modifier.fillMaxSize(),
            indicator = {
                PullToRefreshDefaults.LoadingIndicator(
                    state = pullToRefreshState,
                    isRefreshing = isRefreshing,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            },
        ) {
            when (val state = uiState) {
                is HomeUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        contentAlignment = Alignment.Center
                    ) { CircularWavyProgressIndicator() }
                }

                is HomeUiState.Error -> {

                    ErrorContent(
                        message = state.message,
                        onRetry = viewModel::loadHome,
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    )
                }

                is HomeUiState.Success -> {

                    HomeContent(
                        trendingMovies = state.trendingMovies,
                        popularMovies = state.popularMovies,
                        topRatedMovies = state.topRatedMovies,
                        upcomingMovies = state.upcomingMovies,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
}