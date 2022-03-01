package com.yeyint.movieapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yeyint.movieapp.domain.models.FavouriteMovieVO
import com.yeyint.movieapp.domain.models.MovieVO
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM tbl_favourite")
    fun getAllFavourite() : Flow<List<FavouriteMovieVO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = FavouriteMovieVO::class)
    suspend fun insert(favouriteMovieVO: FavouriteMovieVO) : Long

    @Query("DELETE FROM tbl_favourite WHERE id =:id")
    fun deleteFavourite(id : Int)
}