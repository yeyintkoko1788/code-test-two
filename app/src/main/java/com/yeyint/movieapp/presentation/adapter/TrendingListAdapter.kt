package com.yeyint.movieapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeyint.movieapp.databinding.ViewItemMovieBinding
import com.yeyint.movieapp.domain.models.FavouriteMovieVO
import com.yeyint.movieapp.domain.models.MovieVO

class TrendingListAdapter(private val listener : TrendingMovieListener) : RecyclerView.Adapter<TrendingListAdapter.TrendingMovieViewHolder>(){

    private var movieList = ArrayList<MovieVO>()
    private var favData = ArrayList<FavouriteMovieVO>()

    interface TrendingMovieListener{
        fun onTapTrendingMovie(data : MovieVO)
        fun onTapFavouriteTrending(id : Int, isFavourite : Boolean)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(item : ArrayList<MovieVO>, favData: List<FavouriteMovieVO>){
        movieList.clear()
        for (i in item){
            if (favData.contains(FavouriteMovieVO(i.id))){
                i.isFavourite = true
            }
            movieList.add(i)
        }
        //movieList = item

        notifyDataSetChanged()
    }

    class TrendingMovieViewHolder(private val itemBinding : ViewItemMovieBinding, private val listener: TrendingMovieListener) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(movie : MovieVO){
            itemBinding.movie = movie
            itemBinding.onClickListener = listener
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            itemBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMovieViewHolder {
        return TrendingMovieViewHolder(ViewItemMovieBinding.inflate(LayoutInflater.from(parent.context)),listener)
    }

    override fun onBindViewHolder(holder: TrendingMovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size
}