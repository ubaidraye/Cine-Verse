package com.raye.cineverse.data.repository

import com.raye.cineverse.data.local.dao.WatchListDao
import com.raye.cineverse.data.mapper.findYoutubeTrailerKey
import com.raye.cineverse.data.mapper.toDomain
import com.raye.cineverse.data.mapper.toEntity
import com.raye.cineverse.data.remote.api.TmdbApiService
import com.raye.cineverse.domain.model.Movie
import com.raye.cineverse.domain.model.MovieDetails
import com.raye.cineverse.domain.repository.MovieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: TmdbApiService,
    private val watchListDao: WatchListDao
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

    override suspend fun searchMovies(
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
                val creditsDeferred = async { apiService.getMovieCredits(movieId = movieId) }

                val detailsDto = detailsDeferred.await()
                val videosDto = runCatching { videosDeferred.await() }.getOrNull()
                val creditsDto = runCatching { creditsDeferred.await() }.getOrNull()

                val trailerKey = videosDto?.findYoutubeTrailerKey()
                val castList = creditsDto?.cast?.map { it.toDomain() }
                detailsDto.toDomain(trailerKey, cast = castList)
            }
        }
    }

    override suspend fun addToWatchList(movie: MovieDetails) {
        watchListDao.insertToWatchList(movie.toEntity())
    }

    override suspend fun removeFromWatchList(movieId: Long) {
        watchListDao.removeFromWatchList(movieId)
    }

    override fun isWatchListed(movieId: Long): Flow<Boolean> {
        return watchListDao.isWatchListed(movieId)
    }

    override fun getWatchListMovies(): Flow<List<Movie>> {
        return watchListDao.getAllWatchListMovies().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}