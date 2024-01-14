package com.android.android_academy

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.android_academy.data.models.MovieModel
import com.android.android_academy.data.models.MoviesSearchResponse
import com.android.android_academy.network.Credentials
import com.android.android_academy.network.MovieApi
import com.android.android_academy.network.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class TestRetrofit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_retrofit)
        val btn = findViewById<Button>(R.id.click)
        btn.apply {
            setOnClickListener {
                getRetrofitResponse()
            }

        }
        }
    }

    private fun getRetrofitResponse() {
        val movieApi : MovieApi = NetworkModule.getMovieApi()
        val responseQueue : Call<MovieModel> = movieApi
            .searchForMovieDetails(
                Credentials().getApiKey(),
                "Jack Reacher"
            )
        Log.v("RETROFIT", "Retrofit request " + responseQueue.request().url)

        //asynchronous call(non blocking)
        responseQueue.enqueue(object : Callback<MovieModel>{
            override fun onResponse(
                call: Call<MovieModel>,
                response: Response<MovieModel>
            ) {
                if(response.code() == 200){
                    Log.v("RETROFIT", "Retrofit response " + response.body().toString())
                    Log.v("RETROFIT", ("Movie Model Title " + response.body()?.Title))
                }else{
                    try{
                        Log.v("RETROFIT", "Retrofit error response " + response.message())
                    }catch (e: IOException){
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                Log.v("RETROFIT", "Retrofit on failure response"+t.stackTraceToString())
                t.printStackTrace()
            }

        })
    }
