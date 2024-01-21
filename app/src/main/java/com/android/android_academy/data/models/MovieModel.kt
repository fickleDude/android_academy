package com.android.android_academy.data.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// serialization - converting object into json string (with Gson plugin)
// deserialization - converting json string into object

//single movie search request
//path http://www.omdbapi.com/?apikey=ada59da8&s=Jack Reacher
//JSON example
//    {
//        "Title": "Jack Reacher",
//        "Year": "2012",
//        "imdbID": "tt0790724",
//        "Type": "movie",
//        "Poster": "https://m.media-amazon.com/images/M/MV5BMTM1NjUxMDI3OV5BMl5BanBnXkFtZTcwNjg1ODM3OA@@._V1_SX300.jpg"
//    }
@Parcelize
data class MovieModel (
    @SerialName("Title")
     private val Title : String,
     @SerialName("Year")
     private val Year : String,
     @SerialName("imdbID")
     private val imdbID : String,
     @SerialName("Type")
     private val Type : String,
     @SerialName("Poster")
     private val Poster : String
) : Parcelable {
    fun getId() : String{
        return imdbID
    }
    fun getTitle() : String{
        return Title
    }
    fun getType() : String{
        return Type
    }
    fun getYear() : String{
        return Year
    }
    fun getPoster() : String{
        return Poster
    }
    override fun toString(): String {
        return "MovieModel(Title='$Title', Year='$Year', imdbID='$imdbID', Type='$Type', Poster='$Poster')"
    }
}