package com.yeyint.movieapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yeyint.movieapp.domain.models.MovieVO
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM tbl_movie")
    fun getAllMovie() : Flow<List<MovieVO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MovieVO::class)
    suspend fun insert(movie : MovieVO) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MovieVO::class)
    fun insertAll(vararg data: MovieVO)

    @Query( "SELECT * FROM tbl_movie WHERE isTrending = 1")
    fun getTrendingMovie() : Flow<List<MovieVO>>

    @Query( "SELECT * FROM tbl_movie WHERE isUpcomming = 1")
    fun getUpcomingMovie() : Flow<List<MovieVO>>

    @Query( "SELECT * FROM tbl_movie WHERE isFavourite = 1")
    fun getFavouriteMovie() : Flow<List<MovieVO>>

    @Query( "SELECT * FROM tbl_movie WHERE id=:id ")
    fun isFavourite(id : Int) : MovieVO

    @Query("DELETE FROM tbl_movie WHERE isTrending = 1")
    fun deleteTrendingMovie()

    @Query("DELETE FROM tbl_movie WHERE isUpcomming = 1")
    fun deleteUpcomingMovie()


    @Delete(entity = MovieVO::class)
    fun delete(movie: MovieVO)

    @Query("DELETE FROM tbl_movie")
    suspend fun clearAll()
}