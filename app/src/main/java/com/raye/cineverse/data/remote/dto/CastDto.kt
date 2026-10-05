package com.raye.cineverse.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CastDto(
    val id: Long,
    val name: String?,
    val character: String?,
    @SerializedName("profile_path") val profilePath: String?
)

data class MovieCreditsDto(
    val id: Long,
    val cast: List<CastDto>
)