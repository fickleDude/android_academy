package com.android.android_academy.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.android_academy.data.models.MovieModel
import com.android.android_academy.data.models.MoviesSearchResponse
import retrofit2.Call
import java.io.IOException
import java.util.concurrent.TimeUnit

//bridge between Retrofit and LiveData data
class MovieApiClient private constructor(){

    private var moviesLiveData : MutableLiveData<List<MovieModel>?> = MutableLiveData(emptyList())

    private var moviesSearchListRunnable : RetrieveMoviesRunnable? = null

    companion object {

        @Volatile private var instance: MovieApiClient? = null // Volatile modifier is necessary

        fun getInstance() =
            instance ?: synchronized(this) { // synchronized to avoid concurrency problem
                instance ?: MovieApiClient().also { instance = it }
            }
    }
    fun getMovies(): MutableLiveData<List<MovieModel>?> {
        return moviesLiveData
    }

//    implement search by title API
    fun getMoviesSearchList(title : String){
        if(moviesSearchListRunnable != null){
            moviesSearchListRunnable = null
        }
        //initialize runnable with correct title
        moviesSearchListRunnable = RetrieveMoviesRunnable(title)
        //new runnable thread (submit)
        val handler = AppExecutors.getInstance().networkIO().submit(moviesSearchListRunnable)
        //set timeout for execution method to avoid crushes (schedule)
        AppExecutors.getInstance().networkIO().schedule(object : Runnable{
            //cancelling retrofit call
            override fun run() {
                handler.cancel(true)
            }

        }, 5000, TimeUnit.MICROSECONDS)
    }

    //retrieve data from runnable class: search by title & search by id
    inner class RetrieveMoviesRunnable(private val title : String) : Runnable {

        private var cancelRequest : Boolean = false

        override fun run() {
            //getting response objects
            try{
                val response = getMovieSearchList(title).execute()
                if (cancelRequest){
                    return
                }

                if(response.code() == 200){
                    Log.v("RETROFIT", "Retrofit response " + response.body().toString())
                    //retrieve data from response
                    val list : List<MovieModel> = response.body()?.getMovies() ?: emptyList()
                    //post new data to LiveData
                    moviesLiveData.postValue(list) //use background thread
                }
                else{
                    Log.v("RETROFIT", "Retrofit error response " + response.message())
                    moviesLiveData.postValue(null)
                }
            }catch (e: IOException){
                Log.v("RETROFIT", "Retrofit on failure response "+e.stackTraceToString())
                Log.v("RETROFIT", "Retrofit on failure response " + e.message)
                moviesLiveData.postValue(null)
            }



        }

        //get response queue
        private fun getMovieSearchList(title : String) : Call<MoviesSearchResponse>{
            val movieApi : MovieApi = NetworkModule.getMovieApi()
            val responseQueue : Call<MoviesSearchResponse> = movieApi
                .searchForMoviesByTitle(
                    Credentials().getApiKey(),
                    title
                )
            Log.v("RETROFIT", "Retrofit request " + responseQueue.request().url)
            return responseQueue
        }

        private fun cancelRequest(){
            Log.v("RETROFIT", "Canceling Retrofit request ")
            cancelRequest = true
        }
    }
}