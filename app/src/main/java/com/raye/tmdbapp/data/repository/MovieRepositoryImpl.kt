package com.raye.tmdbapp.data.repository

import com.raye.tmdbapp.data.mapper.findYoutubeTrailerKey
import com.raye.tmdbapp.data.mapper.toDomain
import com.raye.tmdbapp.data.remote.api.TmdbApiService
import com.raye.tmdbapp.domain.model.Movie
import com.raye.tmdbapp.domain.model.MovieDetails
import com.raye.tmdbapp.domain.repository.MovieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: TmdbApiService
) : MovieRepository {

    override suspend fun getTrendingMovies(page: Int): Result<List<Movie>> {
        return runCatching {
            apiService.getTrendingMovies(page = page).results.map { movieDto ->
                movieDto.toDomain()
            }
        }
    }

    override suspend fun getPopularMovies(page: Int): Result<List<Movie>> {
        return runCatching {
            apiService.getPopularMovies(page = page).results.map { movieDto ->
                movieDto.toDomain()
            }
        }
    }

    override suspend fun getTopRatedMovies(page: Int): Result<List<Movie>> {
        return runCatching {
            apiService.getTopRatedMovies(page = page).results.map { movieDto ->
                movieDto.toDomain()
            }
        }
    }

    override suspend fun getUpcomingMovies(page: Int): Result<List<Movie>> {
        return runCatching {
            apiService.getUpcomingMovies(page = page).results.map { movieDto ->
                movieDto.toDomain()
            }
        }
    }

    override suspend fun searchMovie(
        query: String,
        page: Int
    ): Result<List<Movie>> {
        if (query.isBlank()) return Result.success(emptyList())

        return runCatching {
            apiService.searchMovies(query = query, page = page).results.map { movieDto ->
                movieDto.toDomain()
            }
        }
    }

    override suspend fun getMovieDetails(movieId: Long): Result<MovieDetails> {
        return runCatching {
            coroutineScope {
                val detailsDeferred = async { apiService.getMovieDetails(movieId = movieId) }
                val videosDeferred = async { apiService.getMovieVideos(movieId = movieId) }

                val detailsDto = detailsDeferred.await()
                val videosDto = runCatching { videosDeferred.await() }.getOrNull()

                val trailerKey = videosDto?.findYoutubeTrailerKey()
                detailsDto.toDomain(trailerKey)
            }
        }
    }
}