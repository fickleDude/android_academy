package com.android.android_academy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.android_academy.UI.list.MoviesListViewModel
import com.android.android_academy.UI.listMovies.MovieAdapter
import com.android.android_academy.UI.listMovies.MovieListener
import com.android.android_academy.data.models.MovieDetailsModel
import com.android.android_academy.data.models.MovieModel
import com.android.android_academy.remote.Credentials
import com.android.android_academy.remote.MovieApi
import com.android.android_academy.remote.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class TestMVVM : AppCompatActivity(), MovieListener {

    //ViewModel
    private lateinit var viewModel: MoviesListViewModel

    //RecyclerView: holds each movie in the list
    private lateinit var recycler: RecyclerView
    private lateinit var adapter : MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        //initialize activity view
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_mvvm)
        //configure toolbar
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        //set up search view
        setUpSearchView()
        //initialize view model
        viewModel = ViewModelProvider(this)[MoviesListViewModel::class.java]
        //initialize recycler view
        configureRecyclerView()
        observeChanges()
    }

    private fun setUpSearchView(){
        val searchView : SearchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    //call client api method from Main Activity
                    viewModel.searchMovieApi(query, "1")
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }


    //create observer
    private fun observeChanges() {
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

    //initialize RecyclerView and add data to it
    fun configureRecyclerView() {
        //intialize RecyclerView with xml component
        recycler = findViewById(R.id.movie_list)
        //intialize adapter for RecyclerView
        //adapter will tell how to interpret stored data on each item of RecyclerView
        adapter = MovieAdapter(applicationContext, this)
        recycler?.adapter = adapter
    }

//    override fun onImageClick(id: String) {
//        //sent id to movie details layout
//
//    }
    override fun onMovieClick(id: String) {
        super.onMovieClick(id)
        Toast.makeText(this, "POSTER WITH ID $id CLICKED", Toast.LENGTH_SHORT).show()
    }

}





    private fun getMovieDetails(id: String) {
        val movieApi: MovieApi = NetworkModule.getMovieApi()
        val responseQueue: Call<MovieDetailsModel> = movieApi
            .searchForMovieDetails(
                Credentials().getApiKey(),
                id
            )
        Log.v("RETROFIT", "Retrofit request " + responseQueue.request().url)

        //asynchronous call(non blocking)
        responseQueue.enqueue(object : Callback<MovieDetailsModel> {
            override fun onResponse(
                call: Call<MovieDetailsModel>,
                response: Response<MovieDetailsModel>
            ) {
                if (response.code() == 200) {
                    Log.v("RETROFIT", "Retrofit response " + response.body().toString())
                    Log.v("RETROFIT", ("Movie Model Title " + response.body()?.Title))
                } else {
                    try {
                        Log.v("RETROFIT", "Retrofit error response " + response.message())
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<MovieDetailsModel>, t: Throwable) {
                Log.v("RETROFIT", "Retrofit on failure response" + t.stackTraceToString())
                t.printStackTrace()
            }

        })
    }



