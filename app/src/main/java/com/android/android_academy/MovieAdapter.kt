package com.android.android_academy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.android_academy.data.models.Movie

class MovieAdapter():RecyclerView.Adapter<MovieViewHolder>()
 {
     private var movies = emptyList<Movie>()

    private var posterFragmentClickListener: PosterListener? = null

    private fun getItem(position:Int): Movie {
        return movies[position]
    }
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie,
             parent,false)
         posterFragmentClickListener = view.context as PosterListener
         view?.findViewById<View>(R.id.poster)?.apply {
             setOnClickListener{
                 posterFragmentClickListener?.onPosterClickedSwitchToMovieDetails()
             }

        }
         return MovieViewHolder(view)
     }

     override fun getItemCount(): Int {
         return movies.size
     }

     override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
         holder.bind(getItem(position))
     }

     fun bindMovies(newMovies: List<Movie>) {
         movies = newMovies
         notifyDataSetChanged()
     }

 }
