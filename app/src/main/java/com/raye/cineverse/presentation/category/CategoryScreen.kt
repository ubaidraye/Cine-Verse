package com.raye.cineverse.presentation.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.raye.cineverse.presentation.home.components.ErrorContent
import com.raye.cineverse.presentation.home.components.MovieCard
import com.raye.cineverse.util.toUserFriendlyMessage

@Composable
fun CategoryScreen(
    onBackClick: () -> Unit,
    onMovieClick: (movieId: Long) -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val pagingItems = viewModel.moviesPagingFlow.collectAsLazyPagingItems()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(viewModel.category.title) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            windowInsets = WindowInsets(0)
        )

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            when (val refreshState = pagingItems.loadState.refresh) {

                is LoadState.Loading -> {
                    CircularWavyProgressIndicator()
                }

                is LoadState.Error -> {
                    ErrorContent(
                        message = refreshState.error.toUserFriendlyMessage(),
                        onRetry = { pagingItems.retry() },
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    )
                }

                else -> {

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            count = pagingItems.itemCount,
                            key = { index -> "${pagingItems.peek(index)?.id}_$index" }
                        ) { index ->
                            pagingItems[index]?.let { movie ->
                                MovieCard(movie = movie, onClick = onMovieClick)
                            }
                        }

                        if (pagingItems.loadState.append is LoadState.Loading) {
                            item(span = { GridItemSpan(2) }) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularWavyProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
