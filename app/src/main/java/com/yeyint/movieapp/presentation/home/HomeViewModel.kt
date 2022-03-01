package com.yeyint.movieapp.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeyint.movieapp.common.Resource
import com.yeyint.movieapp.data.local.AppDatabase
import com.yeyint.movieapp.data.repository.MovieRepository
import com.yeyint.movieapp.domain.models.FavouriteMovieVO
import com.yeyint.movieapp.domain.models.MovieListResponse
import com.yeyint.movieapp.domain.models.MovieVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository : MovieRepository
): ViewModel(){
    private val _trending_movie_list : MutableLiveData<List<MovieVO>?> = MutableLiveData()
    val trending_movie_list =_trending_movie_list

    private val _trending_error : MutableLiveData<String?> = MutableLiveData()
    val trending_error = _trending_error

    private val _trending_error_visibility : MutableLiveData<Boolean> = MutableLiveData()
    val trending_error_visibility = _trending_error_visibility

    private val _trending_loading : MutableLiveData<Boolean> = MutableLiveData(true)
    val trending_loading = _trending_loading

    private val _comming_movie_list : MutableLiveData<List<MovieVO>?> = MutableLiveData()
    val comming_movie_list =_comming_movie_list

    private val _comming_error : MutableLiveData<String?> = MutableLiveData()
    val comming_error = _comming_error

    private val _comming_error_visibility : MutableLiveData<Boolean> = MutableLiveData()
    val comming_error_visibility = _comming_error_visibility

    private val _comming_loading : MutableLiveData<Boolean> = MutableLiveData(true)
    val comming_loading = _comming_loading

    var favourite_movie : List<FavouriteMovieVO> = emptyList()

    init {
        _comming_error_visibility.value = false
        _comming_error_visibility.value = false
        getFavouriteMovie()
        getTrendingMovie()
        getUpcomingMovie()
    }

    fun setFavourite(movieVO: MovieVO) = viewModelScope.launch{
        movieRepository.setFavourite(movieVO.id,movieVO.isFavourite)
        getFavouriteMovie()
        if (_comming_movie_list.value?.contains(movieVO) == true){
            val temp = _comming_movie_list.value
           temp?.get(temp.indexOf(movieVO))?.isFavourite  = !movieVO.isFavourite
            _comming_movie_list.value = temp
        }
        if (_trending_movie_list.value?.contains(movieVO) == true){
            val temp = _trending_movie_list.value
            temp?.get(temp.indexOf(movieVO))?.isFavourite = !movieVO.isFavourite
            _trending_movie_list.value = temp
        }
    }

    fun getFavouriteMovie() = viewModelScope.launch {
        movieRepository.getFavouriteMovie().collect {
            favourite_movie = it
        }
    }

    fun getTrendingMovie() = viewModelScope.launch {
        movieRepository.getTrendingCacheAndData().collect {
            when(it){
                is Resource.Success -> {
                    _trending_error_visibility.value = false
                    _trending_loading.value = false
                    _trending_movie_list.value = it.data
                }
                is Resource.Error -> {
                    _trending_error_visibility.value = true
                    _trending_loading.value = false
                    _trending_error.value = it.message
                }
                is Resource.Loading -> {
                    _trending_error_visibility.value = false
                    _trending_loading.value = true
                }
            }
        }
    }

    fun getUpcomingMovie() = viewModelScope.launch {
        movieRepository.getUpcomingCacheAndData().collect {
            when(it){
                is Resource.Success -> {
                    _comming_error_visibility.value = false
                    _comming_loading.value = false
                    _comming_movie_list.value = it.data
                }
                is Resource.Error -> {
                    _comming_error_visibility.value = true
                    _comming_loading.value = false
                    _comming_error.value = it.message
                }
                is Resource.Loading -> {
                    _comming_error_visibility.value = false
                    _comming_loading.value = true
                }
            }
        }
    }

}