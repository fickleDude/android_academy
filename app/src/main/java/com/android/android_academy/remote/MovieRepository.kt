package com.android.android_academy.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.android_academy.data.models.MovieModel


//Singleton: creation design pattern that lets you ensure that a class has only one
//instance while providing a global access point to this instance.

//companion object - special type of object that is associated with a class and can be
//used to store static members and methods for the class

//Repository gets data from remote source via API
class MovieRepository private constructor(){

    private val client : MovieApiClient = MovieApiClient.getInstance()

    companion object {

        @Volatile private var instance: MovieRepository? = null // Volatile modifier is necessary

        fun getInstance() =
            instance ?: synchronized(this) { // synchronized to avoid concurrency problem
                instance ?: MovieRepository().also { instance = it }
            }
    }

    fun getMovies(): MutableLiveData<List<MovieModel>?> {
        //client connects to remote DB and gets data according to request
        return  client.getMovies()
    }

    //calling client api method on the background
    fun searchMovieApi(title : String, pageNumber : String){
        client.getMoviesSearchList(title, pageNumber)
    }
}