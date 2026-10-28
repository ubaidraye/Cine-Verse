package com.raye.cineverse.presentation.category

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.raye.cineverse.domain.model.Movie
import com.raye.cineverse.domain.repository.MovieRepository
import com.raye.cineverse.navigation.MovieCategory

class CategoryPagingSource(
    private val repository: MovieRepository,
    private val category: MovieCategory
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        val result = when (category) {

            MovieCategory.TRENDING -> repository.getTrendingMovies(page = page)
            MovieCategory.POPULAR -> repository.getPopularMovies(page = page)
            MovieCategory.TOP_RATED -> repository.getTopRatedMovies(page = page)
            MovieCategory.UPCOMING -> repository.getUpcomingMovies(page = page)
        }

        return result.fold(
            onSuccess = { movies ->
                LoadResult.Page(
                    data = movies,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (movies.isEmpty()) null else page + 1
                )
            },
            onFailure = { exception ->
                LoadResult.Error(exception)
            }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}