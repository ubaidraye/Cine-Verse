package com.raye.cineverse.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raye.cineverse.data.local.dao.WatchListDao
import com.raye.cineverse.data.local.entity.WatchListEntity


@Database(
    entities = [WatchListEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CineVerseDatabase : RoomDatabase() {
    abstract fun watchListDao(): WatchListDao
}