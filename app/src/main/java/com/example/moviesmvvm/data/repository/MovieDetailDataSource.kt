package com.example.moviesmvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesmvvm.data.api.MovieDBInterface
import com.example.moviesmvvm.data.model.MovieDetails
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class MovieDetailDataSource(private val apiService: MovieDBInterface, private val compositeDisposable: CompositeDisposable) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState // to avoid implementing getters and setters

    private val _downloadedMovieResponse = MutableLiveData<MovieDetails>()
    val downloadedMovieDetails: LiveData<MovieDetails>
        get() = _downloadedMovieResponse

    fun getMovieDetails(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe({ movieDetails ->
                        _downloadedMovieResponse.postValue(movieDetails)
                        _networkState.postValue(NetworkState.LOADED)
                    }, {error ->
                        _networkState.postValue(NetworkState.ERROR)
                        Log.e("getMovieDetails", error.message)
                    })
            )
        } catch (ex: IOException) {
            Log.e("getMovieDetails", ex.message)
        }
    }
 }