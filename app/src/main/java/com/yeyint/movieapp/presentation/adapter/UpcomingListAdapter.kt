package com.yeyint.movieapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeyint.movieapp.databinding.ViewItemUpcomingMovieBinding
import com.yeyint.movieapp.domain.models.FavouriteMovieVO
import com.yeyint.movieapp.domain.models.MovieVO

class UpcomingListAdapter(private val listener : UpcomingMovieListener) : RecyclerView.Adapter<UpcomingListAdapter.UpcomingMovieViewHolder>(){
    private var movieList = ArrayList<MovieVO>()

    interface UpcomingMovieListener{
        fun onTapUpcomingMovie(data : MovieVO)
        fun onTapFavouriteUpcoming(id : Int, isFavourite : Boolean)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(item : ArrayList<MovieVO>,favData : List<FavouriteMovieVO>){
        movieList.clear()
        for (i in item){
            if (favData.contains(FavouriteMovieVO(i.id))){
                i.isFavourite = true
            }
            movieList.add(i)
        }
        notifyDataSetChanged()
    }

    class UpcomingMovieViewHolder(private val itemBinding : ViewItemUpcomingMovieBinding,private val listener : UpcomingMovieListener) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(movie : MovieVO){
            itemBinding.movie = movie
            itemBinding.onClickListener = listener
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            itemBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMovieViewHolder {
        return UpcomingMovieViewHolder(ViewItemUpcomingMovieBinding.inflate(LayoutInflater.from(parent.context)),listener)
    }

    override fun onBindViewHolder(holder: UpcomingMovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size
}