package com.android.android_academy

import android.content.Context
import android.database.DatabaseUtils
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.android_academy.data.models.Movie
import com.android.android_academy.domain.MoviesDataSource

class FragmentMoviesListObserver : Fragment(), Observer<List<Movie>> {

    private var recycler: RecyclerView? = null
    lateinit var viewModel: MoviesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[MoviesListViewModel::class.java]
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler = view?.findViewById(R.id.moviesList)
        val adapter = MovieAdapter()
        recycler?.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.movies.observe(this, this)
    }

    private fun updateData() {
        (recycler?.adapter as? MovieAdapter)?.apply {
            bindMovies(MoviesDataSource().getMovies())
        }
    }

    override fun onChanged(value: List<Movie>) {
        updateData()
    }

}