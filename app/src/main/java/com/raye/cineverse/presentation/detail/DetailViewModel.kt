package com.raye.cineverse.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raye.cineverse.domain.model.MovieDetails
import com.raye.cineverse.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val movieId: Long? = savedStateHandle.get<Long>("movieId")

    init {
        movieId?.let { id ->
            loadMovieDetail(id)
        }
    }

    fun retry() {
        movieId?.let { id ->
            loadMovieDetail(id)
        }
    }

    fun loadMovieDetail(id: Long) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading

            val recommendationsDeferred = async {
                repository.getMovieRecommendations(movieId = id).getOrDefault(emptyList())
            }
            val similarDeferred = async {
                repository.getSimilarMovies(movieId = id).getOrDefault(emptyList())
            }

            repository.getMovieDetails(id)
                .onSuccess { details ->
                    val recommendations = recommendationsDeferred.await()
                    val similar = similarDeferred.await()

                    repository.isWatchListed(id).collect { isWatchListed ->
                        _uiState.value = DetailUiState.Success(
                            movieDetails = details,
                            isWatchListed = isWatchListed,
                            recommendations = recommendations,
                            similar = similar
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.value = DetailUiState.Error(
                        exception.localizedMessage ?: "Failed to load details"
                    )
                }
        }
    }

    fun toggleWatchList(movieDetails: MovieDetails, isWatchListed: Boolean) {
        viewModelScope.launch {
            if (isWatchListed) {
                repository.removeFromWatchList(movieDetails.id)
            } else {
                repository.addToWatchList(movieDetails)
            }
        }
    }
}