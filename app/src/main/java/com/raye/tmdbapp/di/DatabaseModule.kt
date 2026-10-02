package com.raye.tmdbapp.di

import android.content.Context
import androidx.room.Room
import com.raye.tmdbapp.data.local.TmdbDatabase
import com.raye.tmdbapp.data.local.dao.WatchListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): TmdbDatabase {
        return Room.databaseBuilder(
            context,
            TmdbDatabase::class.java,
            "tmdb_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWatchListDao(db: TmdbDatabase): WatchListDao {
        return db.watchListDao()
    }
}