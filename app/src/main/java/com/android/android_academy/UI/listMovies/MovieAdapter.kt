package com.android.android_academy.UI.listMovies

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.android_academy.R
import com.android.android_academy.data.models.MovieModel
import com.android.android_academy.domain.Movie


class MovieAdapter(private val context: Context):RecyclerView.Adapter<MovieViewHolder>()
 {
     private var movies = emptyList<MovieModel>()

    private var posterFragmentClickListener: PosterListener? = null

    private fun getItem(position:Int): MovieModel {
        return movies[position]
    }
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
         //identify correct view
         val view = LayoutInflater.from(parent.context).inflate(
             R.layout.movie_list_item,
             parent,false)

//         posterFragmentClickListener = view.context as PosterListener
//         view?.findViewById<View>(R.id.movie_img)?.apply {
//             setOnClickListener{
//                 posterFragmentClickListener?.onPosterClickedSwitchToMovieDetails()
//             }
//
//        }
         return MovieViewHolder(view)
     }

     override fun getItemCount(): Int {
         return movies.size
     }

     override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
         holder.bind(this.context, getItem(position))
     }

     @SuppressLint("NotifyDataSetChanged")
     fun setMovies(newMovies: List<MovieModel>) {
         movies = newMovies
         notifyDataSetChanged()
     }

 }
