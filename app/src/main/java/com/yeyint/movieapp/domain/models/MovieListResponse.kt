package com.yeyint.movieapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class MovieListResponse(
    val page : Int,
    val results : ArrayList<MovieVO>
)

@Entity(tableName = "tbl_movie")
data class MovieVO(
    @PrimaryKey(autoGenerate = true)
    val movieId: Long,
    val video : Boolean,
    val id : Int,
    val overview : String,
    val release_date : String,
    val vote_count : String,
    val adult : Boolean,
    val backdrop_path : String,
    val vote_average : Float,
    val title : String,
    val original_language : String,
    val original_title : String,
    val poster_path : String,
    val popularity : Double,
    val media_type : String?,
    var isFavourite : Boolean = false,
    var isTrending : Boolean = false,
    var isUpcomming : Boolean = false
)