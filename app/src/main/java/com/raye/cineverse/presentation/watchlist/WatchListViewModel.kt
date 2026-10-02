package com.raye.cineverse.presentation.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raye.cineverse.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val uiState: StateFlow<WatchListUiState> = repository.getWatchListMovies()
        .map { movies ->
            if (movies.isEmpty()) {
                WatchListUiState.Empty
            } else {
                WatchListUiState.Success(movies)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WatchListUiState.Loading
        )
}