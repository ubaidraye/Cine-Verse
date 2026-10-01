package com.raye.tmdbapp.domain.repository

import com.raye.tmdbapp.domain.model.Movie
import com.raye.tmdbapp.domain.model.MovieDetails

interface MovieRepository {

    suspend fun getTrendingMovies(page: Int = 1): Result<List<Movie>>

    suspend fun getPopularMovies(page: Int = 1): Result<List<Movie>>

    suspend fun getTopRatedMovies(page: Int = 1): Result<List<Movie>>

    suspend fun getUpcomingMovies(page: Int = 1): Result<List<Movie>>

    suspend fun searchMovie(query: String, page: Int = 1): Result<List<Movie>>

    suspend fun getMovieDetails(movieId: Long): Result<MovieDetails>
}