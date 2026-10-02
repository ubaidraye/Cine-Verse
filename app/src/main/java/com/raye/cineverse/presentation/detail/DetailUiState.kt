package com.raye.cineverse.presentation.detail

import com.raye.cineverse.domain.model.MovieDetails

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(
        val movieDetails: MovieDetails,
        val isWatchListed: Boolean = false
    ) : DetailUiState

    data class Error(val message: String) : DetailUiState
}