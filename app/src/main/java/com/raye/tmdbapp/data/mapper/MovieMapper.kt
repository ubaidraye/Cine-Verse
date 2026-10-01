package com.raye.tmdbapp.data.mapper

import com.raye.tmdbapp.data.remote.dto.MovieDetailsDto
import com.raye.tmdbapp.data.remote.dto.MovieDto
import com.raye.tmdbapp.data.remote.dto.VideoResponseDto
import com.raye.tmdbapp.domain.model.Movie
import com.raye.tmdbapp.domain.model.MovieDetails

private const val IMAGE_BASE_URL_W500 = "https://image.tmdb.org/t/p/w500"
private const val IMAGE_BASE_URL_W700 = "https://image.tmdb.org/t/p/w700"

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        posterUrl = posterPath,
        backdropUrl = backdropPath,
        rating = voteAverage ?: 0.0,
        releaseDate = releaseDate.orEmpty()
    )
}

fun MovieDetailsDto.toDomain(youtubeTrailerKey: String? = null): MovieDetails {
    return MovieDetails(
        id = id,
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        posterUrl = posterPath?.let { "$IMAGE_BASE_URL_W500$it" },
        backdropUrl = backdropPath?.let { "$IMAGE_BASE_URL_W700$it" },
        rating = voteAverage ?: 0.0,
        releaseDate = releaseDate.orEmpty(),
        runtime = runtime ?: 0,
        tagline = tagline.orEmpty(),
        genres = genres?.map { it.name }.orEmpty(),
        youtubeTrailerKey = youtubeTrailerKey
    )
}

fun VideoResponseDto.findYoutubeTrailerKey(): String? {
    return results?.firstOrNull { videoDto ->
        videoDto.site.equals("YouTube", ignoreCase = true) &&
                (videoDto.type.equals("Trailer", ignoreCase = true) ||
                        videoDto.type.equals("Teaser", ignoreCase = true))
    }?.key
}