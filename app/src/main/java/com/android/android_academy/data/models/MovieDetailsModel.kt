package com.android.android_academy.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//Parcelable нужна для передачи сущности от одной активности к другой
//@Parcelize явно описывает процесс сериализации без использования рефлексии, поэтому быстрее @Serializable

//path http://www.omdbapi.com/?i=tt0790724&apikey=ada59da8
//search for movie with id
//JSON example
//{
//    "Title": "Jack Reacher",
//    "Released": "21 Dec 2012",
//    "Plot": "A homicide investigator digs deeper into a case involving a trained military sniper responsible for a mass shooting.",
//    "Poster": "https://m.media-amazon.com/images/M/MV5BMTM1NjUxMDI3OV5BMl5BanBnXkFtZTcwNjg1ODM3OA@@._V1_SX300.jpg",
//    "imdbRating": "7.0",
//    "imdbID": "tt0790724"
//}

@Parcelize
data class MovieDetailsModel(
    val Title: String?,
    val Poster: String?,
    val Released: String?,
    val imdbID: String?,
    val imdbRating: String?,
    val Plot: String?
) : Parcelable {
    override fun toString(): String {
        return "MovieModel(Title=$Title, Poster=$Poster, Released=$Released, imdbID=$imdbID, imdbRating=$imdbRating, Plot=$Plot)"
    }
}

//если нужно специфически реализовать процесс сериализации/десериализации,без аннотации
//пример boilerplate кода
//{
//    constructor(source: Parcel): this(
//        source.readString(),
//        source.readString(),
//        source.readString(),
//        source.readInt(),
//        source.readFloat(),
//        source.readString()
//    )
//
//    companion object : Parceler<MovieModel> {
//        override fun MovieModel.write(dest: Parcel, flags: Int)  = with(dest) {
//            writeValue(title)
//            writeString(poster_path)
//            writeString(release_date)
//            writeValue(movie_id)
//            writeValue(vote_average)
//            writeValue(movie_overview)
//        }
//
//        override fun create(parcel: Parcel): MovieModel {
//            return MovieModel(parcel)
//        }
//    }
//}
