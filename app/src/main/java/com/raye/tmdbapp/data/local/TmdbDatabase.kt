package com.raye.tmdbapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raye.tmdbapp.data.local.dao.WatchListDao
import com.raye.tmdbapp.data.local.entity.WatchListEntity


@Database(
    entities = [WatchListEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TmdbDatabase : RoomDatabase() {
    abstract fun watchListDao(): WatchListDao
}