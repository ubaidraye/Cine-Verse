package com.raye.cineverse.di

import android.content.Context
import androidx.room.Room
import com.raye.cineverse.data.local.CineVerseDatabase
import com.raye.cineverse.data.local.dao.WatchListDao
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
    ): CineVerseDatabase {
        return Room.databaseBuilder(
            context,
            CineVerseDatabase::class.java,
            "tmdb_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWatchListDao(db: CineVerseDatabase): WatchListDao {
        return db.watchListDao()
    }
}