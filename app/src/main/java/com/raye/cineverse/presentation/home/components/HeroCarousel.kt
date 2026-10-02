package com.raye.cineverse.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.raye.cineverse.domain.model.Movie
import com.raye.cineverse.ui.theme.CineVerseTheme
import java.util.Locale


@Composable
fun HeroCarousel(
    trendingMovies: List<Movie>,
    onMovieClick: (movieId: Long) -> Unit,
    modifier: Modifier = Modifier
) {

    if (trendingMovies.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { trendingMovies.size })

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 24.dp),
            pageSpacing = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val movie = trendingMovies[page]

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onMovieClick(movie.id) }
            ) {

                AsyncImage(
                    model = movie.backdropUrl ?: movie.posterUrl,
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "⭐\uFE0F ${
                            String.format(
                                Locale.US,
                                "%.1f",
                                movie.rating
                            )
                        } • ${movie.releaseDate}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(trendingMovies.size) { index ->
                val isSelected = pagerState.currentPage == index

                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(if (isSelected) 24.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHeroCarousel() {
    val movies = listOf(
        Movie(
            id = 1,
            title = "Resident Evil (2026)",
            overview = "Medical courier Bryan unwittingly finds himself fighting for survival as one fateful, horrifying night collapses around him in chaos.\n",
            posterUrl = "https://image.tmdb.org/t/p/w500/i7UyjfPio0VFHB9rBUZSFyhOoM8.jpg",
            backdropUrl = "https://image.tmdb.org/t/p/w780/3icyRAqgakNcQn6aDVz9libFmBA.jpg",
            rating = 71.0,
            releaseDate = "9/18/2026",
        ),
        Movie(
            id = 2,
            title = "The Odyssey (2026)",
            overview = "Odysseus, the legendary King of Ithaca, embarks on a long and perilous journey home following the Trojan War. Throughout his voyage, he is forced to confront the whims of gods, mythological monsters, and trials that stretch both his cunning and his humanity to the breaking point.\n",
            posterUrl = "https://www.themoviedb.org/t/p/w500/5rhTDKUhPYvpdQIijFIs5VoWsON.jpg",
            backdropUrl = "https://media.themoviedb.org/t/p/w780/iuylzRSllrGn7YB322kwKoOVMcq.jpg",
            rating = 80.0,
            releaseDate = "7/17/2026",
        ),
        Movie(
            id = 3,
            title = "The Odyssey (2026)",
            overview = "Odysseus, the legendary King of Ithaca, embarks on a long and perilous journey home following the Trojan War. Throughout his voyage, he is forced to confront the whims of gods, mythological monsters, and trials that stretch both his cunning and his humanity to the breaking point.\n",
            posterUrl = "https://www.themoviedb.org/t/p/w500/5rhTDKUhPYvpdQIijFIs5VoWsON.jpg",
            backdropUrl = "https://media.themoviedb.org/t/p/w780/iuylzRSllrGn7YB322kwKoOVMcq.jpg",
            rating = 80.0,
            releaseDate = "7/17/2026",
        ),
        Movie(
            id = 4,
            title = "The Odyssey (2026)",
            overview = "Odysseus, the legendary King of Ithaca, embarks on a long and perilous journey home following the Trojan War. Throughout his voyage, he is forced to confront the whims of gods, mythological monsters, and trials that stretch both his cunning and his humanity to the breaking point.\n",
            posterUrl = "https://www.themoviedb.org/t/p/w500/5rhTDKUhPYvpdQIijFIs5VoWsON.jpg",
            backdropUrl = "https://media.themoviedb.org/t/p/w780/iuylzRSllrGn7YB322kwKoOVMcq.jpg",
            rating = 80.0,
            releaseDate = "7/17/2026",
        )
    )
    CineVerseTheme {
        HeroCarousel(
            trendingMovies = movies,
            onMovieClick = {}
        )
    }
}