package com.raye.tmdbapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raye.tmdbapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadHome() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            coroutineScope {
                val trendingDeferred = async { repository.getTrendingMovies() }
                val popularDeferred = async { repository.getPopularMovies() }
                val topRatedDeferred = async { repository.getTopRatedMovies() }
                val upcomingDeferred = async { repository.getUpcomingMovies() }

                val trendingResult = trendingDeferred.await()
                val popularResult = popularDeferred.await()
                val topRatedResult = topRatedDeferred.await()
                val upcomingResult = upcomingDeferred.await()

                val failure = listOf(trendingResult, popularResult, topRatedResult, upcomingResult)
                    .firstOrNull { it.isFailure }

                if (failure != null) {
                    _uiState.value = HomeUiState.Error(
                        failure.exceptionOrNull()?.localizedMessage ?: "Failed to load home feed"
                    )
                } else {
                    _uiState.value = HomeUiState.Success(
                        trendingMovies = trendingResult.getOrThrow(),
                        popularMovies = popularResult.getOrThrow(),
                        topRatedMovies = topRatedResult.getOrThrow(),
                        upcomingMovies = upcomingResult.getOrThrow()
                    )
                }
            }
        }
    }

    init {
        loadHome()
    }
}