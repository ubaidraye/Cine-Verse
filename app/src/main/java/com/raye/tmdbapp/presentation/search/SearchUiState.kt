package com.raye.tmdbapp.presentation.search

import com.raye.tmdbapp.domain.model.Movie

sealed interface SearchUiState {

    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(val movies: List<Movie>) : SearchUiState
    data object Empty : SearchUiState
    data class Error(val message: String) : SearchUiState
}