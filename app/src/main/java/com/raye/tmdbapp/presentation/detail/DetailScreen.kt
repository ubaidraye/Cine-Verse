package com.raye.tmdbapp.presentation.detail

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import java.util.Locale

@Composable
fun DetailScreen(
    onBackClick: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    fun openYoutubeTrailer(key: String) {
        val intent = Intent(Intent.ACTION_VIEW, "https://www.youtube.com/watch?v=$key".toUri())
        context.startActivity(intent)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is DetailUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is DetailUiState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            is DetailUiState.Success -> {
                val movie = state.movieDetails

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Backdrop Banner + Play Trailer Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                    ) {

                        AsyncImage(
                            model = movie.backdropUrl ?: movie.posterUrl,
                            contentDescription = movie.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Dark Gradient Overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black.copy(alpha = 0.4f),
                                            Color.Transparent,
                                            MaterialTheme.colorScheme.background
                                        )
                                    )
                                )
                        )

                        // Back Button
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .statusBarsPadding()
                                .padding(8.dp)
                                .align(Alignment.TopStart)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }

                        // Play Trailer FAB (If YouTube Trailer Key Exists)
                        movie.youtubeTrailerKey?.let { key ->
                            ExtendedFloatingActionButton(
                                onClick = { openYoutubeTrailer(key) },
                                icon = {
                                    Icon(
                                        Icons.Default.PlayArrow,
                                        contentDescription = "Play Trailer"
                                    )
                                },
                                text = { Text("Watch Trailer") },
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(16.dp)
                            )
                        }
                    }

                    // Poster + Title + Rating
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        if (movie.tagline.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "\"${movie.tagline}\"",
                                style = MaterialTheme.typography.bodyMedium,
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "⭐ ${String.format(Locale.US, "%.1f", movie.rating)}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = "⏱ ${movie.runtime} min",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = movie.releaseDate,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Genre Chips
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            movie.genres.forEach { genre ->
                                AssistChip(
                                    onClick = {},
                                    label = { Text(genre) })
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Storyline Overview
                        Text(
                            text = "Storyline",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = movie.overview,
                            style = MaterialTheme.typography.bodyLarge,
                            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.3
                        )
                    }
                }
            }
        }
    }
}