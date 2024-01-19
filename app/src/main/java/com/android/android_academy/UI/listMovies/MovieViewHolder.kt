package com.android.android_academy.UI.listMovies

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.android_academy.R
import com.android.android_academy.data.models.MovieModel
import com.android.android_academy.domain.Movie
import com.bumptech.glide.Glide
import kotlinx.serialization.SerialName

//holds MovieModel(Title, Year, imdbID, Type, Poster)
class MovieViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val title : TextView = view.findViewById(R.id.title)
    private val type : TextView = view.findViewById(R.id.type)
    private val year : TextView = view.findViewById(R.id.year)
    private val poster : ImageView = view.findViewById(R.id.poster)

    fun bind(context: Context, movie : MovieModel){
        title.text = movie.getTitle()
        type.text = movie.getType()
        year.text = movie.getYear()

        Glide.with(context).load(movie.getPoster()).into(poster)
    }
}
