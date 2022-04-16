package com.jefriap.submission1made.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.data.local.entity.TvShowEntity

@Database(entities = [MovieEntity::class, TvShowEntity::class], version = 2, exportSchema = false)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}