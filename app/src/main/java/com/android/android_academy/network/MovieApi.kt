package com.android.android_academy.network

import com.android.android_academy.data.models.MovieModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieApi {
    //path http://www.omdbapi.com/?t={movie title to search for}&apikey={api_key}
//    @Headers({
//        "Accept: */*",
//        "Accept-Encoding: gzip, deflate, br",
//        "User-Agent: com.android.android_academy"
//    })
    @GET(".")
    fun searchForMovieDetails(
        @Query("t") movieTitle :String
//        @Query("api_key") apiKey :String
//        @Header("Authorization") authHeader : String
    ) : Call<MovieModel>

}