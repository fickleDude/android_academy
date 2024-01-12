package com.android.android_academy

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.android_academy.data.models.Movie
import com.android.android_academy.domain.MoviesData
import kotlinx.coroutines.launch

class MoviesListViewModel : ViewModel() {
    var movies : LiveData<List<Movie>> = MoviesData().getMovies()

    fun addMovie(movie: Movie) = viewModelScope.launch {
        MoviesData().addMovie(movie)
    }

}