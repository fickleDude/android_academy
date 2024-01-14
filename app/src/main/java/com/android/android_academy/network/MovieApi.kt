package com.android.android_academy.network

import com.android.android_academy.data.models.MovieDetailsModel
import com.android.android_academy.data.models.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    //path http://www.omdbapi.com/?apikey={api_key}&t={movie title to search for}
    @GET(".")
    fun searchForMovieDetails(
        @Query("apikey") apiKey :String,
        @Query("i") movieId :String
    ) : Call<MovieDetailsModel>

    //path http://www.omdbapi.com/?apikey=ada59da8&s=Jack Reacher
    @GET(".")
    fun searchForMoviesByTitle(
        @Query("apikey") apiKey :String,
        @Query("s") title :String
    ) : Call<MoviesSearchResponse>

}