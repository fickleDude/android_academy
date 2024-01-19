package com.android.android_academy.UI.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.android_academy.remote.MovieRepository
import com.android.android_academy.data.models.MovieModel


//class extends base ViewModel class so ViewModelProvider can identify it
class MoviesListViewModel(
    private val repository : MovieRepository = MovieRepository.getInstance()
) : ViewModel() {


    fun getMovies():LiveData<List<MovieModel>?>{
        //get access to data via repository which uses API to connect to remote DB
        return  repository.getMovies()
    }

    //calling repository method which calls client api method
    fun searchMovieApi(title : String){
        repository.searchMovieApi(title)
    }
}
