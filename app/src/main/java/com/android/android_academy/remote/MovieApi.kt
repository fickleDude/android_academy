package com.android.android_academy.remote

import com.android.android_academy.data.models.MovieDetailsModel
import com.android.android_academy.data.models.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//API FOR RETROFIT
interface MovieApi {
    //path http://www.omdbapi.com/?apikey={api_key}&t={movie title to search for} - GET MOVIE BY ID
    @GET(".")
    fun searchForMovieDetails(
        @Query("apikey") apiKey :String,
        @Query("i") movieId :String
    ) : Call<MovieDetailsModel>

    //path http://www.omdbapi.com/?apikey=ada59da8&s=Jack Reacher - GET MOVIES BY TITLE&page=2
    @GET(".")
    fun searchForMoviesByTitle(
        @Query("apikey") apiKey :String,
        @Query("s") title :String,
        @Query("page") pageNumber :String
    ) : Call<MoviesSearchResponse>

}