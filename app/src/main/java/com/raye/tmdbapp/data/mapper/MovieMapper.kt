package com.raye.tmdbapp.data.mapper

import com.raye.tmdbapp.data.local.entity.WatchListEntity
import com.raye.tmdbapp.data.remote.dto.MovieDetailsDto
import com.raye.tmdbapp.data.remote.dto.MovieDto
import com.raye.tmdbapp.data.remote.dto.VideoResponseDto
import com.raye.tmdbapp.domain.model.Movie
import com.raye.tmdbapp.domain.model.MovieDetails
import kotlin.text.orEmpty

private const val IMAGE_BASE_URL_W500 = "https://image.tmdb.org/t/p/w500"
private const val IMAGE_BASE_URL_W780 = "https://image.tmdb.org/t/p/w780"

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        posterUrl = posterPath?.let { "$IMAGE_BASE_URL_W500$it" },
        backdropUrl = backdropPath?.let { "$IMAGE_BASE_URL_W780$it" },
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
        backdropUrl = backdropPath?.let { "$IMAGE_BASE_URL_W780$it" },
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


fun MovieDetails.toEntity(): WatchListEntity {
    return WatchListEntity(
        id = id,
        title = title,
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        rating = rating,
        releaseDate = releaseDate,
    )
}

fun WatchListEntity.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = "",
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        rating = rating,
        releaseDate = releaseDate.orEmpty(),
    )
}
