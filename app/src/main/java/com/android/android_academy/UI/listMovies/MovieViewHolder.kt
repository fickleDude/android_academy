package com.android.android_academy.UI.listMovies

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.android_academy.R
import com.android.android_academy.data.models.MovieModel
import com.android.android_academy.domain.Movie
import com.bumptech.glide.Glide
import kotlinx.serialization.SerialName
import java.util.Random

//holds MovieModel(Title, Year, imdbID, Type, Poster)
class MovieViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    //movie_list_item widgets
    private val title : TextView = view.findViewById(R.id.movie_title)
    private val type : TextView = view.findViewById(R.id.movie_type)
    private val year : TextView = view.findViewById(R.id.movie_year)
    private val poster : ImageView = view.findViewById(R.id.movie_img)
    private val ratingBar : RatingBar = view.findViewById(R.id.rating_bar)


    fun bind(context: Context, movie : MovieModel){
        title.text = movie.getTitle()
        type.text = movie.getType()
        year.text = movie.getYear()

        ratingBar.rating = Random().nextInt(5).toFloat()

        Glide.with(context).load(movie.getPoster()).into(poster)
    }
}
