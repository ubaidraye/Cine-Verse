package com.raye.tmdbapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raye.tmdbapp.data.local.entity.WatchListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToWatchList(movie: WatchListEntity)

    @Query("DELETE FROM watchlist WHERE id = :movieId")
    suspend fun removeFromWatchList(movieId: Long)

    @Query("SELECT * FROM watchlist ORDER BY addedAt DESC")
    fun getAllWatchListMovies(): Flow<List<WatchListEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE id = :movieId)")
    fun isWatchListed(movieId: Long): Flow<Boolean>
}