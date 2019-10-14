package com.example.moviesmvvm.ui.single_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesmvvm.data.model.MovieDetails
import com.example.moviesmvvm.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel (private val movieDetailsRepository: MovieDetailsRepository, movieId: Int): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val movieDetails: LiveData<MovieDetails> by lazy {
        movieDetailsRepository.getSingleMovieDetails(compositeDisposable, movieId)
    }

    val movieDetailsNetworkState: LiveData<NetworkState> by lazy {
        movieDetailsRepository.getMovieNetworkStatus()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}