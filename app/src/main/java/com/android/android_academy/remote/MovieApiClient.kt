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

    //data for search by title
    private var moviesLiveData : MutableLiveData<List<MovieModel>?> = MutableLiveData(emptyList())
    //data for search by id
    private var movieDetails : MutableLiveData<MovieDetailsModel?> = MutableLiveData()
    //data for search "popular"
    private var popularMoviesLiveData : MutableLiveData<List<MovieModel>?> = MutableLiveData(emptyList())

    private var moviesSearchListRunnable : RetrieveMoviesListRunnable? = null
    private var moviesPopularRunnable : RetrievePopularMoviesRunnable? = null
    private var moviesMovieDetailsRunnable : RetrieveMovieDetailsRunnable? = null

    companion object {

        @Volatile private var instance: MovieApiClient? = null // Volatile modifier is necessary

        fun getInstance() =
            instance ?: synchronized(this) { // synchronized to avoid concurrency problem
                instance ?: MovieApiClient().also { instance = it }
            }
    }
//    GETTERS FOR REPOSITORY
    fun getMovies(): MutableLiveData<List<MovieModel>?> {
        return moviesLiveData
    }

    fun getPopularMovies(): MutableLiveData<List<MovieModel>?> {
        return popularMoviesLiveData
    }
    fun getMovieDetails() : MutableLiveData<MovieDetailsModel?> {
        return movieDetails
    }

//  CALLS
    //search by title
    fun retrieveMoviesSearchList(title : String, pageNumber : String){
        if(moviesSearchListRunnable != null){
            moviesSearchListRunnable = null
        }
        //initialize runnable with correct title
        moviesSearchListRunnable = RetrieveMoviesListRunnable(title, pageNumber)
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

    //search popular movies
    fun retrievePopularMovies(pageNumber : String){
        if(moviesPopularRunnable != null){
            moviesPopularRunnable = null
        }
        //initialize runnable with correct title
        moviesPopularRunnable = RetrievePopularMoviesRunnable(pageNumber)
        //new runnable thread (submit)
        val handler = AppExecutors.getInstance().networkIO().submit(moviesPopularRunnable)
        //set timeout for execution method to avoid crushes (schedule)
        AppExecutors.getInstance().networkIO().schedule(object : Runnable{
            //cancelling retrofit call
            override fun run() {
                handler.cancel(true)
            }

        }, 3000, TimeUnit.MILLISECONDS)
    }

    //search by id
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




//    RUNNABLES
    //search by title
    inner class RetrieveMoviesListRunnable(private val title : String, private val pageNumber : String) : Runnable {

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
    //search for "popular"
    inner class RetrievePopularMoviesRunnable(private val pageNumber : String) : Runnable {

        private var cancelRequest : Boolean = false

        override fun run() {
            //getting response objects
            try{
                val response = getMovieSearchList("batman", pageNumber).execute()
                if (cancelRequest){
                    return
                }

                if(response.code() == 200){
                    Log.v("RETROFIT", "Retrofit response " + response.body().toString())
                    //retrieve data from response
                    val list : List<MovieModel> = response.body()?.getMovies() ?: emptyList()
                    //post new data to LiveData
                    popularMoviesLiveData.postValue(list) //use background thread
                }
                else{
                    Log.v("RETROFIT", "Retrofit error response " + response.message())
                    popularMoviesLiveData.postValue(null)
                }
            }catch (e: IOException){
                Log.v("RETROFIT", "Retrofit on failure response "+e.stackTraceToString())
                Log.v("RETROFIT", "Retrofit on failure response " + e.message)
                popularMoviesLiveData.postValue(null)
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

    //search by id
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
