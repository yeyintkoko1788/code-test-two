package com.yeyint.movieapp.domain.models

data class MovieDetailResponse(
    val backdrop_path : String,
    val genres : ArrayList<GenresVO>,
    val id : Int,
    val original_language : String,
    val original_title : String,
    val overview : String,
    val spoken_languages : ArrayList<LanguageVO>,
    val release_date : String,
    val status : String,
    val title : String,
    val vote_average : String,
    val vote_count : String,
    var isFavourite : Boolean
)

data class LanguageVO(
    val english_name : String,
    val iso_639_1 : String,
    val name : String
)

data class GenresVO(
    val id : Int,
    val name : String,
)