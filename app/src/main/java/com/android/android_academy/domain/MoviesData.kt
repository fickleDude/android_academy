package com.android.android_academy.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.android_academy.data.models.Movie

class MoviesData {
    private var movies : LiveData<List<Movie>> = MutableLiveData(listOf(
        Movie("Action, Adventure, Drama",
            "Avengers: End Game",
            "res/drawable/img.png"),
        Movie("Action, Adventure, Drama",
            "Avengers: End Game",
            "drawable/img.png"),
        Movie("Action, Adventure, Drama",
            "Avengers: End Game",
            "app/src/main/res/drawable/img.png"),
        Movie("Action, Adventure, Drama",
            "Avengers: End Game",
            "@drawable/img"),
        Movie("Action, Adventure, Drama",
            "Avengers: End Game",
            "D:/U24/android/android_academy/app/src/main/res/drawable/img.png"),
        Movie("Action, Adventure, Drama",
            "Avengers: End Game",
            "D:\\U24\\android\\android_academy\\app\\src\\main\\res\\drawable\\img.png"),
    ))

    fun getMovies(): LiveData<List<Movie>> {
        return movies
    }

    fun addMovie(movie: Movie) {
        movies.value?.plus(movie)
    }
}