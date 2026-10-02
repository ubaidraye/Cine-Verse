package com.raye.tmdbapp.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raye.tmdbapp.domain.model.Movie


@Composable
fun MovieSectionRow(
    sectionTitle: String,
    movies: List<Movie>,
    onMovieClick: (movieId: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {

        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(movies, key = { it.id }) { movie ->
                MovieCard(movie = movie, onClick = { onMovieClick(movie.id) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMovieSection() {
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

    MovieSectionRow(sectionTitle = "Popular Titles", movies = movies, onMovieClick = {})
}