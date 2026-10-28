package com.raye.cineverse.presentation.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raye.cineverse.domain.model.Movie
import com.raye.cineverse.domain.repository.MovieRepository
import com.raye.cineverse.navigation.MovieCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val categoryName: String? = savedStateHandle.get<String>("categoryType")
    val category: MovieCategory = categoryName?.let {
        runCatching { MovieCategory.valueOf(it) }.getOrDefault(MovieCategory.TRENDING)
    } ?: MovieCategory.TRENDING

    val moviesPagingFlow: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        )
    ) {
        CategoryPagingSource(repository, category)
    }.flow.cachedIn(viewModelScope)
}
