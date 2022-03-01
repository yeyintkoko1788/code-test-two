package com.yeyint.movieapp.data

import com.yeyint.movieapp.domain.models.MovieListResponse
import com.yeyint.movieapp.common.Constant
import com.yeyint.movieapp.domain.models.MovieDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET(Constant.TRANDING_MOVIE)
    suspend fun getTrendingMovieList(
        @Query("api_key") apiKey : String
    ) : Response<MovieListResponse>

    @GET(Constant.UPCOMING_MOVIE)
    suspend fun getUpcomingMovieList(
        @Query("api_key") apiKey : String
    ) : Response<MovieListResponse>

    @GET("movie/{movieID}")
    suspend fun getMovieDetail(
        @Path("movieID") movieID: Int,
        @Query("api_key") apiKey : String
    ) : Response<MovieDetailResponse>
}