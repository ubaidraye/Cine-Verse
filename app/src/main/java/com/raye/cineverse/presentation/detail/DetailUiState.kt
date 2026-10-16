package com.raye.cineverse.presentation.detail

import com.raye.cineverse.domain.model.Movie
import com.raye.cineverse.domain.model.MovieDetails

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(
        val movieDetails: MovieDetails,
        val isWatchListed: Boolean = false,
        val recommendations: List<Movie> = emptyList(),
        val similar: List<Movie> = emptyList()
    ) : DetailUiState

    data class Error(val message: String) : DetailUiState
}