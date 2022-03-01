package com.yeyint.movieapp.data.repository

import androidx.room.withTransaction
import com.yeyint.movieapp.common.Resource
import com.yeyint.movieapp.common.networkBoundResource
import com.yeyint.movieapp.data.local.AppDatabase
import com.yeyint.movieapp.data.remote.BaseDataSource
import com.yeyint.movieapp.data.remote.MovieRemoteDataSource
import com.yeyint.movieapp.domain.models.FavouriteMovieVO
import com.yeyint.movieapp.domain.models.MovieDetailResponse
import com.yeyint.movieapp.domain.models.MovieListResponse
import com.yeyint.movieapp.domain.models.MovieVO
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieRemoteDataSource : MovieRemoteDataSource,
    private val appDataBase : AppDatabase
) :  BaseDataSource() {
   suspend fun getTrendingMovie(): Flow<Resource<MovieListResponse>> {
        return  flow {
            emit(Resource.Loading())

            emit(movieRemoteDataSource.getTrendingMovieList())
        }.flowOn(Dispatchers.IO)
    }

    fun getFavouriteMovie() = appDataBase.favouriteDao().getAllFavourite()

    suspend fun setFavourite(id : Int, isFavourite : Boolean){
        if (isFavourite)
            appDataBase.favouriteDao().deleteFavourite(id)
        else
            appDataBase.favouriteDao().insert(FavouriteMovieVO(id))
    }

    suspend fun getTrendingCacheAndData() = networkBoundResource(
        query = {
            appDataBase.movieDao().getTrendingMovie()
        },
        fetch = {
            movieRemoteDataSource.getTrendingMovieList()
        },
        saveFetchResult = {
            appDataBase.withTransaction {
                if (it.data != null){
                    appDataBase.movieDao().deleteTrendingMovie()
                    val data = it.data.results
                    for (i in data.indices){
                        data[i].isTrending = true
                    }
                    data.let { it1 ->
                        appDataBase.movieDao().insertAll(*it1.toTypedArray())
                    }
                }
            }
        }
    )

    suspend fun getUpcomingMovie() : Flow<Resource<MovieListResponse>> {
        return  flow {
            emit(Resource.Loading())
            emit(movieRemoteDataSource.getUpcomingMovieLis())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUpcomingCacheAndData() = networkBoundResource(
        query = {
            appDataBase.movieDao().getUpcomingMovie()
        },
        fetch = {
            movieRemoteDataSource.getUpcomingMovieLis()
        },
        saveFetchResult = {
            appDataBase.withTransaction {
                if (it.data != null){
                    appDataBase.movieDao().deleteUpcomingMovie()
                    val data = it.data.results
                    for (i in data.indices){
                        data[i].isUpcomming = true
                    }
                    data.let { it1 ->
                        appDataBase.movieDao().insertAll(*it1.toTypedArray())
                    }
                }
            }
        }
    )

    suspend fun getMovieDetail(movieID : Int) : Flow<Resource<MovieDetailResponse>> {
        return  flow {
            emit(Resource.Loading())
            emit(movieRemoteDataSource.getMovieDetail(movieID))
        }.flowOn(Dispatchers.IO)
    }
}