package com.android.android_academy

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.android_academy.data.models.Movie

class MovieViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val title : TextView = view.findViewById(R.id.title)
    private val tag : TextView = view.findViewById(R.id.tag)
    private val poster : View = view.findViewById(R.id.poster)

    fun bind(movie : Movie){
        title.text = movie.title
        tag.text = movie.tag
        //poster.background = Drawable.createFromPath(movie.poster)
    }
}
