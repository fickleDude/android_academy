package com.android.android_academy.network

import com.android.android_academy.data.models.MovieModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieApi {
    //path http://www.omdbapi.com/?apikey={api_key}&t={movie title to search for}
    @GET(".")
    fun searchForMovieDetails(
        @Query("apikey") apiKey :String,
        @Query("t") movieTitle :String
    ) : Call<MovieModel>

}