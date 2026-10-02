package com.raye.tmdbapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raye.tmdbapp.presentation.home.components.HeroCarousel
import com.raye.tmdbapp.presentation.home.components.MovieSectionRow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (movieId: Long) -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(title = { Text("Cine Verse") }, windowInsets = WindowInsets(0))
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (val state = uiState) {
                is HomeUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is HomeUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = { viewModel.loadHome() }
                        ) { Text("Retry") }
                    }
                }

                is HomeUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        item {
                            HeroCarousel(
                                trendingMovies = state.trendingMovies,
                                onMovieClick = onMovieClick
                            )
                        }

                        item {
                            MovieSectionRow(
                                sectionTitle = "Popular Movies",
                                movies = state.popularMovies,
                                onMovieClick = onMovieClick
                            )
                        }

                        item {
                            MovieSectionRow(
                                sectionTitle = "Top Rated",
                                movies = state.topRatedMovies,
                                onMovieClick = onMovieClick
                            )
                        }

                        item {
                            MovieSectionRow(
                                sectionTitle = "Upcoming",
                                movies = state.upcomingMovies,
                                onMovieClick = onMovieClick
                            )
                        }
                    }
                }
            }
        }
    }
}