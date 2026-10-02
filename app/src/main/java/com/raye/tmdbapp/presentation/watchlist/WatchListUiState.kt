package com.raye.tmdbapp.presentation.watchlist

import com.raye.tmdbapp.domain.model.Movie


sealed interface WatchListUiState {
    data object Loading : WatchListUiState
    data object Empty : WatchListUiState
    data class Success(val movies: List<Movie>) : WatchListUiState
}