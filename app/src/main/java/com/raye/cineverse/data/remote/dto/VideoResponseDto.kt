package com.raye.cineverse.data.remote.dto

data class VideoResponseDto(
    val id: Int,
    val results: List<VideoDto>?
)