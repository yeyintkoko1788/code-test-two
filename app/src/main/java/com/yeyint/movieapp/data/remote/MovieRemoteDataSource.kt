package com.yeyint.movieapp.data.remote

import com.yeyint.movieapp.common.Constant
import com.yeyint.movieapp.data.MovieApiService
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService : MovieApiService
) : BaseDataSource(){
    suspend fun getTrendingMovieList() = getResult { movieService.getTrendingMovieList(Constant.API_KEY) }

    suspend fun getUpcomingMovieLis() = getResult { movieService.getUpcomingMovieList(Constant.API_KEY) }

    suspend fun getMovieDetail(movieID : Int) = getResult { movieService.getMovieDetail(movieID,Constant.API_KEY) }
}