//package com.android.android_academy
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import com.android.android_academy.data.models.MovieDetailsModel
//import com.android.android_academy.data.models.MovieModel
//import com.android.android_academy.data.models.MoviesSearchResponse
//import com.android.android_academy.remote.Credentials
//import com.android.android_academy.remote.MovieApi
//import com.android.android_academy.remote.NetworkModule
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.io.IOException
//
//class TestRetrofit : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_test_retrofit)
//        val btn = findViewById<Button>(R.id.click)
//        btn.apply {
//            setOnClickListener {
//       //         getMovieDetails("tt0790724")
//                getMovieSearchList("Jack Reacher")
//            }
//
//        }
//        }
//    }
//
//    private fun getMovieDetails(id : String) {
//        val movieApi : MovieApi = NetworkModule.getMovieApi()
//        val responseQueue : Call<MovieDetailsModel> = movieApi
//            .searchForMovieDetails(
//                Credentials().getApiKey(),
//                id
//            )
//        Log.v("RETROFIT", "Retrofit request " + responseQueue.request().url)
//
//        //asynchronous call(non blocking)
//        responseQueue.enqueue(object : Callback<MovieDetailsModel>{
//            override fun onResponse(
//                call: Call<MovieDetailsModel>,
//                response: Response<MovieDetailsModel>
//            ) {
//                if(response.code() == 200){
//                    Log.v("RETROFIT", "Retrofit response " + response.body().toString())
//                    Log.v("RETROFIT", ("Movie Model Title " + response.body()?.Title))
//                }else{
//                    try{
//                        Log.v("RETROFIT", "Retrofit error response " + response.message())
//                    }catch (e: IOException){
//                        e.printStackTrace()
//                    }
//                }
//            }
//            override fun onFailure(call: Call<MovieDetailsModel>, t: Throwable) {
//                Log.v("RETROFIT", "Retrofit on failure response"+t.stackTraceToString())
//                t.printStackTrace()
//            }
//
//        })
//    }
//
//    private fun getMovieSearchList(title : String){
//        val movieApi : MovieApi = NetworkModule.getMovieApi()
//        val responseQueue : Call<MoviesSearchResponse> = movieApi
//            .searchForMoviesByTitle(
//                Credentials().getApiKey(),
//                title
//            )
//        Log.v("RETROFIT", "Retrofit request " + responseQueue.request().url)
//        responseQueue.enqueue(object : Callback<MoviesSearchResponse>{
//            override fun onResponse(
//                call: Call<MoviesSearchResponse>,
//                response: Response<MoviesSearchResponse>
//            ) {
//                if(response.code() == 200){
//                   // Log.v("RETROFIT", "Retrofit response " + response.body()?.getMovies().toString())
//                    Log.v("RETROFIT", "Retrofit response " + response.body().toString())
//                    val moviesSearch : MoviesSearchResponse = response.body()!!
//
//                    val movies : List<MovieModel> = response.body()?.getMovies() ?: emptyList()
//                    for(movie : MovieModel in movies){
//                        Log.v("RETROFIT", ("Movie Model ${movie.toString()}"))
//                    }
//                }else{
//                    try{
//                        Log.v("RETROFIT", "Retrofit error response " + response.message())
//                    }catch (e: IOException){
//                        e.printStackTrace()
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<MoviesSearchResponse>, t: Throwable) {
//                Log.v("RETROFIT", "Retrofit on failure response"+t.stackTraceToString())
//                t.printStackTrace()
//            }
//
//        })
//    }
