package com.raye.tmdbapp.data.remote.dto

data class VideoResponseDto(
    val id: Int,
    val results: List<VideoDto>?
)