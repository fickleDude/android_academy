package com.android.android_academy.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

//    val client =  OkHttpClient.Builder()
//        .addInterceptor(Acc)
//        .build()

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