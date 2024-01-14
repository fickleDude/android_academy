package com.android.android_academy.data.models

import com.google.gson.annotations.Expose
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//search for movies list by title
//path http://www.omdbapi.com/?apikey=ada59da8&s=Jack Reacher
//JSON example
//{
//    "Search": [
//    {
//        "Title": "Jack Reacher",
//        "Year": "2012",
//        "imdbID": "tt0790724",
//        "Type": "movie",
//        "Poster": "https://m.media-amazon.com/images/M/MV5BMTM1NjUxMDI3OV5BMl5BanBnXkFtZTcwNjg1ODM3OA@@._V1_SX300.jpg"
//    },
//    {...},
//    {...},
//    ],
//    "totalResults": "7",
//    "Response": "True"
//}
@Serializable
data class MoviesSearchResponse(
    private var Search : List<MovieModel>,
    private var totalResults : String
) {
    fun getMovies():List<MovieModel>{
        return Search
    }
}