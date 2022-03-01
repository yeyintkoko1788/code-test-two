package com.yeyint.movieapp.presentation.adapter

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yeyint.movieapp.BaseApplication
import com.yeyint.movieapp.R
import com.yeyint.movieapp.common.Constant
import com.yeyint.movieapp.data.local.AppDatabase
import com.yeyint.movieapp.domain.models.FavouriteMovieVO
import com.yeyint.movieapp.domain.models.GenresVO
import com.yeyint.movieapp.domain.models.LanguageVO
import com.yeyint.movieapp.domain.models.MovieVO

@BindingAdapter("app:imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    Glide.with(BaseApplication.getInstance())
        .load(Constant.BASE_IMAGE_URL+imgUrl)
        .into(imgView)
}

@BindingAdapter("app:trendingList","app:favouriteList")
fun bindRecyclerView(recyclerView: RecyclerView, data : List<MovieVO>?, favData : List<FavouriteMovieVO>){
    val adapter = recyclerView.adapter as TrendingListAdapter
    if (data != null) {
        adapter.setItem(data as ArrayList<MovieVO>, favData)
    }
}

@BindingAdapter("app:upcomingList","app:favouriteList")
fun bindUpcomingRecyclerView(recyclerView: RecyclerView, data :List<MovieVO>?, favData : List<FavouriteMovieVO>){
    val adapter = recyclerView.adapter as UpcomingListAdapter
    if (data != null) {
        adapter.setItem(data as ArrayList<MovieVO>, favData)
    }
}

@BindingAdapter("app:setGenresText")
fun bindGenresText(view : AppCompatTextView, data : ArrayList<GenresVO>?){
    var genreString = ""
    if (!data.isNullOrEmpty()){
        for ((i, genre) in data.withIndex()){
            if (i != data.size-1)
                genreString = genreString.plus(genre.name).plus(" | ")
            else
                genreString = genreString.plus(genre.name)
        }
        view.text = genreString
    }
}

@BindingAdapter("app:setLanguageText")
fun bindLanguageText(view : AppCompatTextView, data : ArrayList<LanguageVO>?){
    var languageString = ""
    if (!data.isNullOrEmpty()){
        for ((i, genre) in data.withIndex()){
            if (i != data.size-1)
                languageString = languageString.plus(genre.name).plus(" | ")
            else
                languageString = languageString.plus(genre.name)
        }
        view.text = languageString
    }
}

@BindingAdapter("app:setFavourite")
fun setFavourite(view : AppCompatImageView, isFavourite : Boolean){
    if (isFavourite){
        view.setImageResource(R.drawable.ic_baseline_star_24)
    }else{
        view.setImageResource(R.drawable.ic_baseline_star_outline_24)
    }
}

