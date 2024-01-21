package com.android.android_academy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.android_academy.UI.MoviesListViewModel
import com.android.android_academy.UI.MovieAdapter
import com.android.android_academy.UI.MovieListener
import com.android.android_academy.data.models.MovieModel

class MovieListActivity : AppCompatActivity(), MovieListener {

    //ViewModel
    private lateinit var viewModel: MoviesListViewModel

    //RecyclerView: holds each movie in the list
    private lateinit var recycler: RecyclerView
    private lateinit var adapter : MovieAdapter

    private var isPopular : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        //initialize activity view
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        //configure toolbar
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        //set up search view
        setUpSearchView()
        //initialize view model
        viewModel = ViewModelProvider(this)[MoviesListViewModel::class.java]
        //initialize recycler view
        configureRecyclerView()
        observeListChanges()
        observePopularChanges()

        //get data for popular movies
        viewModel.searchPopularMovies("1")

    }

    private fun setUpSearchView(){
        val searchView : SearchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    //call client api method from Main Activity
                    viewModel.searchByTitleMovieApi(query, "1")
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        //use to switch view from popular to list
        searchView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                isPopular = false
            }

        })
    }


    //create observer
    private fun observeListChanges() {
        viewModel.getMovies().observe(this, object : Observer<List<MovieModel>?> {
            override fun onChanged(value: List<MovieModel>?) {
                if (value != null) {
                    for (movie: MovieModel in value) {
                        Log.v("RETROFIT", ("Movie Model $movie"))
                        adapter.setMovies(value)
                    }
                }
            }

        })
    }

    private fun observePopularChanges() {
        viewModel.getPopularMovies().observe(this, object : Observer<List<MovieModel>?> {
            override fun onChanged(value: List<MovieModel>?) {
                if (value != null && isPopular) {
                    for (movie: MovieModel in value) {
                        Log.v("RETROFIT", ("Movie Model $movie"))
                        adapter.setMovies(value)
                    }
                }
            }

        })
    }

    //initialize RecyclerView and add data to it
    private fun configureRecyclerView() {
        //intialize RecyclerView with xml component
        recycler = findViewById(R.id.movie_list)
        //intialize adapter for RecyclerView
        //adapter will tell how to interpret stored data on each item of RecyclerView
        adapter = MovieAdapter(applicationContext, this)
        recycler?.adapter = adapter

        //recycler view pagination(add on scroll listener)
        //loading next page of api response
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(!recyclerView.canScrollVertically(1)){
                    //need to display new page of request result
                    viewModel.searchNextPage()
                }
            }
        })
    }

    override fun onMovieClick(position: Int) {
        //parse clicked MovieModel to adapter in order to get id and search for movie details by id
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("movie", adapter.getItem(position))
        startActivity(intent)
    }

}



