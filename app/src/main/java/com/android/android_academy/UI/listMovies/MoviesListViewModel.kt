package com.android.android_academy.UI.listMovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.android_academy.domain.Movie
import kotlinx.coroutines.launch

class MoviesListViewModel : ViewModel() {
    var movies : LiveData<List<Movie>> = MutableLiveData(emptyList())

    fun addMovie(movie: Movie) = viewModelScope.launch {
        movies.value?.plus(movie)
    }

}