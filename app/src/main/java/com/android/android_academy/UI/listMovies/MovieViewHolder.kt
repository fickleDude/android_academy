package com.android.android_academy.UI.listMovies

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.android_academy.R
import com.android.android_academy.domain.Movie
import com.bumptech.glide.Glide

class MovieViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val title : TextView = view.findViewById(R.id.title)
    private val tag : TextView = view.findViewById(R.id.tag)
    private val poster : ImageView = view.findViewById(R.id.poster)

    fun bind(context: Context, movie : Movie){
        title.text = movie.title
        tag.text = movie.tag


        Glide.with(context).load(movie.poster).into(poster)
    }
}
