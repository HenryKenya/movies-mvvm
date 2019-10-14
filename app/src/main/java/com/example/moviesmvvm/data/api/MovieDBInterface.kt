package com.example.moviesmvvm.data.api

import com.example.moviesmvvm.data.model.MovieDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDBInterface {
    // https://api.themoviedb.org/3/movie/475557?api_key=c73ddf3fafef8413a27d7d47aad8e602
    // https://api.themoviedb.org/3/movie/popular?api_key=c73ddf3fafef8413a27d7d47aad8e602
    // https://api.themoviedb.org/3/
    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails> {

    }
}