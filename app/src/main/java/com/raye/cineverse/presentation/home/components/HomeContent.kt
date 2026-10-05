package com.raye.cineverse.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raye.cineverse.domain.model.Movie

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    trendingMovies: List<Movie>,
    popularMovies: List<Movie>,
    topRatedMovies: List<Movie>,
    upcomingMovies: List<Movie>,
    onMovieClick: (movieId: Long) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            HeroCarousel(
                trendingMovies = trendingMovies,
                onMovieClick = onMovieClick
            )
        }

        item {
            MovieSectionRow(
                sectionTitle = "Popular Movies",
                movies = popularMovies,
                onMovieClick = onMovieClick
            )
        }

        item {
            MovieSectionRow(
                sectionTitle = "Top Rated",
                movies = topRatedMovies,
                onMovieClick = onMovieClick
            )
        }

        item {
            MovieSectionRow(
                sectionTitle = "Upcoming",
                movies = upcomingMovies,
                onMovieClick = onMovieClick
            )
        }
    }
}