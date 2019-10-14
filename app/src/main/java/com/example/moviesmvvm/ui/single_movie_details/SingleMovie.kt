package com.example.moviesmvvm.ui.single_movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders.of
import com.bumptech.glide.Glide
import com.example.moviesmvvm.R
import com.example.moviesmvvm.data.api.MovieDBClient
import com.example.moviesmvvm.data.api.MovieDBInterface
import com.example.moviesmvvm.data.api.POSTER_BASE_URL
import com.example.moviesmvvm.data.model.MovieDetails
import com.example.moviesmvvm.data.repository.NetworkState
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {
    private lateinit var movieViewModel: SingleMovieViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)
        val movieId = intent.getIntExtra("movieId", 1)

        val apiService: MovieDBInterface = MovieDBClient.getClient()
        movieDetailsRepository = MovieDetailsRepository(apiService)
        movieViewModel = getViewModel(movieId)

        movieViewModel.movieDetails.observe(this, Observer { bindUI(it) })

        movieViewModel.movieDetailsNetworkState.observe(this, Observer {
            progress_bar.visibility = if (it === NetworkState.LOADING) VISIBLE else GONE
            text_error_text.visibility = if (it === NetworkState.ERROR) VISIBLE else GONE
        })
    }

    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return of(this, object:ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieDetailsRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
    private fun bindUI(movie: MovieDetails?) {
        movie_title.text = movie?.title
        movie_tagline.text = movie?.tagline
        movie_release_date.text = movie?.releaseDate
        movie_rating.text = movie?.rating.toString()
        movie_runtime.text = "${movie?.runtime.toString()} minutes"
        movie_overview.text = movie?.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(movie?.budget)
        movie_revenue.text = formatCurrency.format(movie?.revenue)

        val moviePosterUrl = POSTER_BASE_URL + movie?.posterPath
        Glide.with(this).load(moviePosterUrl).into(iv_movie_poster)
    }
}
