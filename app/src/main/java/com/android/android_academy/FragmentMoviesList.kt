package com.android.android_academy

import android.content.Context
import android.database.DatabaseUtils
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.android_academy.domain.MoviesDataSource

class FragmentMoviesList : Fragment() {
    private var recycler: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler = view?.findViewById(R.id.moviesList)
        recycler?.adapter = MovieAdapter()
    }

    override fun onStart() {
        super.onStart()

        updateData()
    }

    private fun updateData() {
        (recycler?.adapter as? MovieAdapter)?.apply {
            bindMovies(MoviesDataSource().getMovies())
        }
    }
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        recycler = view?.findViewById(R.id.moviesList)
//        recycler?.adapter = MovieAdapter(context,MoviesDataSource().getMovies())
//    }
}