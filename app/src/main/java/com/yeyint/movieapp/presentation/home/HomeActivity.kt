package com.yeyint.movieapp.presentation.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeyint.movieapp.common.SpaceItemDecoration
import com.yeyint.movieapp.databinding.ActivityMainBinding
import com.yeyint.movieapp.domain.models.MovieVO
import com.yeyint.movieapp.presentation.BaseActivity
import com.yeyint.movieapp.presentation.adapter.TrendingListAdapter
import com.yeyint.movieapp.presentation.adapter.UpcomingListAdapter
import com.yeyint.movieapp.presentation.moviedetail.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity(), UpcomingListAdapter.UpcomingMovieListener,
    TrendingListAdapter.TrendingMovieListener {

    private lateinit var binding : ActivityMainBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val itemDecoration = SpaceItemDecoration(10,3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTrending.layoutManager = layoutManager
        binding.rvTrending.addItemDecoration(itemDecoration)
        binding.rvTrending.adapter = TrendingListAdapter(this)

        val layoutManagerVertical =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvUpcomming.layoutManager = layoutManagerVertical
        binding.rvUpcomming.adapter = UpcomingListAdapter(this)
    }

    override fun onTapUpcomingMovie(data: MovieVO) {
        startActivity(MovieDetailActivity.getIntent(data.id))
    }

    override fun onTapFavouriteUpcoming(movie : MovieVO) {
        viewModel.setFavourite(movie)
    }


    override fun onTapTrendingMovie(data: MovieVO) {
        startActivity(MovieDetailActivity.getIntent(data.id))
    }

    override fun onTapFavouriteTrending(movie: MovieVO) {
        viewModel.setFavourite(movie)
    }
}