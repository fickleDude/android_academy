package com.android.android_academy.UI.listMovies

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.android_academy.R
import com.android.android_academy.data.models.MovieModel


class MovieAdapter(private val context: Context, private var clickListener : MovieListener):RecyclerView.Adapter<MovieViewHolder>(), View.OnClickListener
 {
     private var movies = emptyList<MovieModel>()
     private lateinit var holder : MovieViewHolder

    fun getItem(position:Int): MovieModel? {
        if(movies.size > position){
            return movies[position]
        }
        return null
    }
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
         //identify correct view
         val view = LayoutInflater.from(parent.context).inflate(
             R.layout.movie_list_item,
             parent,false)

         holder = MovieViewHolder(view)
         holder.itemView.setOnClickListener(this)
         return holder
     }

     override fun getItemCount(): Int {
         return movies.size
     }

     override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
         getItem(position)?.let { holder.bind(this.context, it) }
     }

     @SuppressLint("NotifyDataSetChanged")
     fun setMovies(newMovies: List<MovieModel>) {
         movies = newMovies
         notifyDataSetChanged()
     }

     override fun onClick(v: View?) {
         clickListener.onMovieClick(holder.adapterPosition)
     }


 }
