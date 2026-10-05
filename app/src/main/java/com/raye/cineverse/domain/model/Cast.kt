package com.raye.cineverse.domain.model

data class Cast(
    val id: Long,
    val name: String,
    val character: String,
    val profileUrl: String?
)