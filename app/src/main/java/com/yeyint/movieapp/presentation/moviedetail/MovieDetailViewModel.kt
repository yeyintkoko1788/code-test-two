package com.yeyint.movieapp.presentation.moviedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeyint.movieapp.common.Resource
import com.yeyint.movieapp.data.repository.MovieRepository
import com.yeyint.movieapp.domain.models.FavouriteMovieVO
import com.yeyint.movieapp.domain.models.MovieDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository : MovieRepository
) : ViewModel() {

    private val _movie_detail : MutableLiveData<MovieDetailResponse?> = MutableLiveData()
    val movieDetail = _movie_detail

    private val _movie_detail_error : MutableLiveData<String?> = MutableLiveData()
    val movie_detail_error = _movie_detail_error

    private val _detail_visibility : MutableLiveData<Boolean> = MutableLiveData()
    val detail_visibility = _detail_visibility

    private val _error_visibility : MutableLiveData<Boolean> = MutableLiveData()
    val error_visibility = _error_visibility

    private val _movie_detail_loading : MutableLiveData<Boolean> = MutableLiveData()
    val movie_detail_loading = _movie_detail_loading

    var favourite_movie : List<FavouriteMovieVO> = emptyList()

    init {
        _detail_visibility.value = false
        _movie_detail_loading.value = false
        _error_visibility.value = false
        getFavouriteMovie()
    }

    fun setFavourite(id : Int, isFavourite : Boolean) = viewModelScope.launch{
        movieRepository.setFavourite(id,isFavourite)
    }

    fun getFavouriteMovie() = viewModelScope.launch {
        movieRepository.getFavouriteMovie().collect {
            favourite_movie = it
        }
    }

    fun getMovieDetail(movieID : Int) = viewModelScope.launch {
        movieRepository.getMovieDetail(movieID).collect {
            when(it){
                is Resource.Success -> {
                    _error_visibility.value = false
                    _detail_visibility.value = true
                    _movie_detail_loading.value = false
                    val data = it.data
                    if (favourite_movie.contains(FavouriteMovieVO(data?.id!!)))
                        data.isFavourite = true
                    _movie_detail.value = data
                }
                is Resource.Error -> {
                    _error_visibility.value = true
                    _detail_visibility.value = false
                    _movie_detail_loading.value = false
                    _movie_detail_error.value = it.message
                }
                is Resource.Loading -> {
                    _error_visibility.value = false
                    _detail_visibility.value = false
                    _movie_detail_loading.value = true
                }
            }
        }
    }
}