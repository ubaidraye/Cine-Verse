package com.raye.tmdbapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieResponseDto(
    val page: Int,
    val results: List<MovieDto>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)