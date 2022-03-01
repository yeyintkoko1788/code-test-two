package com.yeyint.movieapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_favourite")
data class FavouriteMovieVO(
    @PrimaryKey
    var id : Int
)