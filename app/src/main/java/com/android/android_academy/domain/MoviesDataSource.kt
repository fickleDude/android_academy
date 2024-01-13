package com.android.android_academy.domain

class MoviesDataSource {
    fun getMovies(): List<Movie> {
        return listOf(
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
        )
    }
}