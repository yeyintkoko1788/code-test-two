package com.yeyint.movieapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yeyint.movieapp.common.Converter
import com.yeyint.movieapp.domain.models.FavouriteMovieVO
import com.yeyint.movieapp.domain.models.MovieVO

@Database(entities = [MovieVO::class, FavouriteMovieVO::class],version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun movieDao() : MovieDao
    abstract fun favouriteDao() : FavouriteDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "movie-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}