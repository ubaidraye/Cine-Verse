package com.raye.tmdbapp.domain.model

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val rating: Double,
    val releaseDate: String
)