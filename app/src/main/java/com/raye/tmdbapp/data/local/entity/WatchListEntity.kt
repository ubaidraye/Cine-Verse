package com.raye.tmdbapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class WatchListEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val rating: Double,
    val releaseDate: String?,
    val addedAt: Long = System.currentTimeMillis()
)