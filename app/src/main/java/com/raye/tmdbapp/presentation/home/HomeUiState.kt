package com.raye.tmdbapp.presentation.home

import com.raye.tmdbapp.domain.model.Movie

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Success(
        val trendingMovies: List<Movie>,
        val popularMovies: List<Movie>,
        val topRatedMovies: List<Movie>,
        val upcomingMovies: List<Movie>
    ) : HomeUiState

    data class Error(val message: String) : HomeUiState
}