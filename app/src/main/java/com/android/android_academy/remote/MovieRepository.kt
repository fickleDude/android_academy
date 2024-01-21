package com.android.android_academy.remote

import androidx.lifecycle.MutableLiveData
import com.android.android_academy.data.models.MovieDetailsModel
import com.android.android_academy.data.models.MovieModel


//Singleton: creation design pattern that lets you ensure that a class has only one
//instance while providing a global access point to this instance.

//companion object - special type of object that is associated with a class and can be
//used to store static members and methods for the class

//Repository gets data from remote source via API
class MovieRepository private constructor(){

    //for next page of request(RecyclerView Pagination)
    private lateinit var query : String
    private lateinit var pageNumber : String


    private val client : MovieApiClient = MovieApiClient.getInstance()

    companion object {

        @Volatile private var instance: MovieRepository? = null // Volatile modifier is necessary

        fun getInstance() =
            instance ?: synchronized(this) { // synchronized to avoid concurrency problem
                instance ?: MovieRepository().also { instance = it }
            }
    }

    fun getMoviesList(): MutableLiveData<List<MovieModel>?> {
        //client connects to remote DB and gets data according to request
        return  client.getMovies()
    }

    fun getPopularMovies(): MutableLiveData<List<MovieModel>?> {
        //client connects to remote DB and gets data according to request
        return  client.getPopularMovies()
    }

    fun getMovieDetails(): MutableLiveData<MovieDetailsModel?> {
        //client connects to remote DB and gets data according to request
        return  client.getMovieDetails()
    }

    //calling client api method on the background
    fun searchMovieByTitleApi(title : String, pageNumber : String){
        query = title
        this.pageNumber = pageNumber
        client.retrieveMoviesSearchList(title, pageNumber)
    }

    fun searchPopularMovies(pageNumber : String){
        client.retrievePopularMovies(pageNumber)
    }

    //calling client api method on the background
    fun searchMovieByIdApi(id : String){
        client.retrieveMovieDetails(id)
    }

//    RecyclerView Pagination
    fun searchNextPage(){
        searchMovieByTitleApi(query, (pageNumber.toInt()+1).toString())
    }
}