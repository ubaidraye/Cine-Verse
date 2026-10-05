package com.raye.cineverse.data.mapper

import com.raye.cineverse.data.local.entity.WatchListEntity
import com.raye.cineverse.data.remote.dto.CastDto
import com.raye.cineverse.data.remote.dto.MovieDetailsDto
import com.raye.cineverse.data.remote.dto.MovieDto
import com.raye.cineverse.data.remote.dto.VideoResponseDto
import com.raye.cineverse.domain.model.Cast
import com.raye.cineverse.domain.model.Movie
import com.raye.cineverse.domain.model.MovieDetails

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

fun MovieDetailsDto.toDomain(
    youtubeTrailerKey: String? = null,
    cast: List<Cast>?
): MovieDetails {
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
        youtubeTrailerKey = youtubeTrailerKey,
        cast = cast.orEmpty()
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

fun CastDto.toDomain(): Cast {
    return Cast(
        id = id,
        name = name.orEmpty(),
        character = character.orEmpty(),
        profileUrl = profilePath?.let { "$IMAGE_BASE_URL_W500$it" }
    )
}