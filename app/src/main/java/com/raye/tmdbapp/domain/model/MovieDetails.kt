package com.raye.tmdbapp.domain.model

data class MovieDetails(
    val id: Long,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val rating: Double,
    val releaseDate: String,
    val runtime: Int,
    val tagline: String,
    val genres: List<String>,
    val youtubeTrailerKey:String?
)