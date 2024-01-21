package com.android.android_academy.UI

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.android_academy.data.models.MovieDetailsModel
import com.android.android_academy.remote.MovieRepository
import com.android.android_academy.data.models.MovieModel


//class extends base ViewModel class so ViewModelProvider can identify it
class MoviesListViewModel(
    private val repository : MovieRepository = MovieRepository.getInstance()
) : ViewModel() {


    fun getMovies():LiveData<List<MovieModel>?>{
        //get access to data via repository which uses API to connect to remote DB
        return  repository.getMoviesList()
    }

    fun getPopularMovies():LiveData<List<MovieModel>?>{
        //get access to data via repository which uses API to connect to remote DB
        return  repository.getPopularMovies()
    }

    fun getMovieDetails():LiveData<MovieDetailsModel?>{
        //get access to data via repository which uses API to connect to remote DB
        return  repository.getMovieDetails()
    }

    //calling repository method which calls client api method
    fun searchByTitleMovieApi(title : String, pageNumber : String){
        repository.searchMovieByTitleApi(title, pageNumber)
    }

    fun searchPopularMovies(pageNumber : String){
        repository.searchPopularMovies(pageNumber)
    }

    fun searchByIdMovieApi(id : String){
        return repository.searchMovieByIdApi(id)
    }

    fun searchNextPage(){
        repository.searchNextPage()
    }
}
