package com.android.android_academy.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.android_academy.data.models.MovieDetailsModel
import com.android.android_academy.data.models.MovieModel
import com.android.android_academy.data.models.MoviesSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

//bridge between Retrofit and LiveData data
class MovieApiClient private constructor(){

    private var moviesLiveData : MutableLiveData<List<MovieModel>?> = MutableLiveData(emptyList())
    private var movieDetails : MutableLiveData<MovieDetailsModel?> = MutableLiveData()

    private var moviesSearchListRunnable : RetrieveMoviesRunnable? = null
    private var moviesMovieDetailsRunnable : RetrieveMovieDetailsRunnable? = null

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
    fun retrieveMoviesSearchList(title : String, pageNumber : String){
        if(moviesSearchListRunnable != null){
            moviesSearchListRunnable = null
        }
        //initialize runnable with correct title
        moviesSearchListRunnable = RetrieveMoviesRunnable(title, pageNumber)
        //new runnable thread (submit)
        val handler = AppExecutors.getInstance().networkIO().submit(moviesSearchListRunnable)
        //set timeout for execution method to avoid crushes (schedule)
        AppExecutors.getInstance().networkIO().schedule(object : Runnable{
            //cancelling retrofit call
            override fun run() {
                handler.cancel(true)
            }

        }, 3000, TimeUnit.MILLISECONDS)
    }

    //    implement search by ID
    fun retrieveMovieDetails(id : String){
        if(moviesMovieDetailsRunnable != null){
            moviesMovieDetailsRunnable = null
        }
        //initialize runnable with correct title
        moviesMovieDetailsRunnable = RetrieveMovieDetailsRunnable(id)
        //new runnable thread (submit)
        val handler = AppExecutors.getInstance().networkIO().submit(moviesMovieDetailsRunnable)
        //set timeout for execution method to avoid crushes (schedule)
        AppExecutors.getInstance().networkIO().schedule(object : Runnable{
            //cancelling retrofit call
            override fun run() {
                handler.cancel(true)
            }

        }, 3000, TimeUnit.MILLISECONDS)
    }

    fun getMovieDetails() : MutableLiveData<MovieDetailsModel?> {
        return movieDetails
    }



    //retrieve data from runnable class: search by title & search by id
    inner class RetrieveMoviesRunnable(private val title : String, private val pageNumber : String) : Runnable {

        private var cancelRequest : Boolean = false

        override fun run() {
            //getting response objects
            try{
                val response = getMovieSearchList(title, pageNumber).execute()
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

        //SEARCH FOR MOVIE LIST
        private fun getMovieSearchList(title : String, pageNumber : String) : Call<MoviesSearchResponse>{
            val movieApi : MovieApi = NetworkModule.getMovieApi()
            val responseQueue : Call<MoviesSearchResponse> = movieApi
                .searchForMoviesByTitle(
                    Credentials().getApiKey(),
                    title,
                    pageNumber
                )
            Log.v("RETROFIT", "Retrofit request " + responseQueue.request().url)
            return responseQueue
        }

        private fun cancelRequest(){
            Log.v("RETROFIT", "Canceling Retrofit request ")
            cancelRequest = true
        }
    }

    //SEARCH FOR MOVIE DETAILS
    inner class RetrieveMovieDetailsRunnable(private val id : String) : Runnable {

        private var cancelRequest : Boolean = false

        override fun run() {
            //getting response objects
            try{
                val response = getMovieDetails(id).execute()
                if (cancelRequest){
                    return
                }

                if(response.code() == 200){
                    Log.v("RETROFIT", "Retrofit response " + response.body().toString())
                    //retrieve data from response
                    movieDetails.postValue(response.body())
                }
                else{
                    Log.v("RETROFIT", "Retrofit error response " + response.message())
                    movieDetails.postValue(null)
                }
            }catch (e: IOException){
                Log.v("RETROFIT", "Retrofit on failure response "+e.stackTraceToString())
                Log.v("RETROFIT", "Retrofit on failure response " + e.message)
                movieDetails.postValue(null)
            }



        }

        //get response queue
        private fun getMovieDetails(id : String) : Call<MovieDetailsModel>{
            val movieApi : MovieApi = NetworkModule.getMovieApi()
            val responseQueue : Call<MovieDetailsModel> = movieApi
                .searchForMovieDetails(
                    Credentials().getApiKey(),
                    id
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

private fun retrieveMovieDetails(id: String) {
    val movieApi: MovieApi = NetworkModule.getMovieApi()
    val responseQueue: Call<MovieDetailsModel> = movieApi
        .searchForMovieDetails(
            Credentials().getApiKey(),
            id
        )
    Log.v("RETROFIT", "Retrofit request " + responseQueue.request().url)

    //asynchronous call(non blocking)
    responseQueue.enqueue(object : Callback<MovieDetailsModel> {
        override fun onResponse(
            call: Call<MovieDetailsModel>,
            response: Response<MovieDetailsModel>
        ) {
            if (response.code() == 200) {
                Log.v("RETROFIT", "Retrofit response " + response.body().toString())
                Log.v("RETROFIT", ("Movie Model Title " + response.body()?.Title))
            } else {
                try {
                    Log.v("RETROFIT", "Retrofit error response " + response.message())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        override fun onFailure(call: Call<MovieDetailsModel>, t: Throwable) {
            Log.v("RETROFIT", "Retrofit on failure response" + t.stackTraceToString())
            t.printStackTrace()
        }

    })
}