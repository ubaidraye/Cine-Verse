package com.raye.cineverse.presentation.watchlist

import com.raye.cineverse.domain.model.Movie


sealed interface WatchListUiState {
    data object Loading : WatchListUiState
    data object Empty : WatchListUiState
    data class Success(val movies: List<Movie>) : WatchListUiState
}