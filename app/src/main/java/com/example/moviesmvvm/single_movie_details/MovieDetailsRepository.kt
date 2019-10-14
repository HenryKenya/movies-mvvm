package com.example.moviesmvvm.single_movie_details

import androidx.lifecycle.LiveData
import com.example.moviesmvvm.data.api.MovieDBInterface
import com.example.moviesmvvm.data.model.MovieDetails
import com.example.moviesmvvm.data.repository.MovieDetailDataSource
import com.example.moviesmvvm.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository (private val apiService: MovieDBInterface) {
    lateinit var movieDetailDataSource: MovieDetailDataSource

    fun getSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int): LiveData<MovieDetails> {
        movieDetailDataSource = MovieDetailDataSource(apiService, compositeDisposable)
        movieDetailDataSource.getMovieDetails(movieId)
        return movieDetailDataSource.downloadedMovieResponse
    }

    fun getMovieNetworkStatus(): LiveData<NetworkState> {
        return movieDetailDataSource.networkState
    }
}