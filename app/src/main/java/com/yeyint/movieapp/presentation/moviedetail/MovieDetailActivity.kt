package com.yeyint.movieapp.presentation.moviedetail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.yeyint.movieapp.BaseApplication
import com.yeyint.movieapp.R
import com.yeyint.movieapp.databinding.ActivityMovieDetailBinding
import com.yeyint.movieapp.presentation.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailActivity : BaseActivity() {

    private lateinit var binding : ActivityMovieDetailBinding
    private val viewModel by viewModels<MovieDetailViewModel>()
    private var movieId : Int = 0

    companion object {
        private val IE_MOVIE_ID = "MOVIE_ID"
        fun getIntent(movieId : Int) : Intent {
            val intent = Intent(BaseApplication.getInstance(), MovieDetailActivity::class.java)
            intent.putExtra(IE_MOVIE_ID,movieId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar(binding.toolbar,true)
        supportActionBar?.title = ""
        setup()
        window.apply {
            statusBarColor = ContextCompat.getColor(
                BaseApplication.getInstance(),
                R.color.color_transparent
            )
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)

        movieId = intent.getIntExtra(IE_MOVIE_ID,0)

        binding.lifecycleOwner = this
        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
        binding.movieId = movieId

        if (savedInstanceState == null)
            getMovieDetail()
    }

    private fun getMovieDetail(){
        viewModel.getMovieDetail(movieId)
    }

    private fun setup() {
        val radius = resources.getDimension(R.dimen.margin_medium_3)
        binding.ivCover.shapeAppearanceModel = binding.ivCover.shapeAppearanceModel
            .toBuilder()
            .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
            .setBottomRightCorner(CornerFamily.ROUNDED, radius)
            .build()
    }

}