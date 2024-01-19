package com.android.android_academy.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//RETROFIT INSTANCE
object NetworkModule {

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(Credentials().getBaseUrl())
        .addConverterFactory(GsonConverterFactory.create())

    private val retrofit = retrofitBuilder.build()

    private val movieApi : MovieApi =
        retrofit.create(MovieApi::class.java)

    fun getMovieApi() : MovieApi {
        return movieApi
    }

}