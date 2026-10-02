package com.raye.tmdbapp.presentation.detail

import com.raye.tmdbapp.domain.model.MovieDetails

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(
        val movieDetails: MovieDetails
    ) : DetailUiState

    data class Error(val message: String) : DetailUiState
}