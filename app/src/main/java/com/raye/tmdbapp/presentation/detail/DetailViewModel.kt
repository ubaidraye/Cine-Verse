package com.raye.tmdbapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raye.tmdbapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun loadMovieDetail(id: Long) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading

            repository.getMovieDetails(id)
                .onSuccess { details ->
                    _uiState.value = DetailUiState.Success(
                        movieDetails = details,
                    )

                }
                .onFailure { exception ->
                    _uiState.value = DetailUiState.Error(
                        exception.localizedMessage ?: "Failed to load details"
                    )
                }
        }
    }
}