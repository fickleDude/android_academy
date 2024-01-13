package com.android.android_academy.UI.listMovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.android_academy.R
import com.android.android_academy.domain.Movie
import com.android.android_academy.domain.MoviesDataSource


class MoviesListFragment : Fragment(), Observer<List<Movie>> {

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
        val adapter = MovieAdapter(view.context)
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
//        createGetMovieCall()
        updateData()
    }

//    private fun getImageAsync() {
////        Log.d(TAG, "getImageAsync")
//        coroutineScope.launch {
//            createGetMovieCall().enqueue(object : Callback {
//                override fun onFailure(call: Call, e: IOException) {
//                   // asyncOnFailureFunction(e)
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                   asyncOnResponseFunction(response)
//                }
//            })
//        }
//    }
//
//    private fun asyncOnResponseFunction(response: Response) {
//        coroutineScope.launch {
//            val content = response.body?.string() ?: ""
//            val json = Json { ignoreUnknownKeys = true }
//            val movie = json.decodeFromString<Movie>(content)
//            viewModel.addMovie(movie)
//        }
//    }

//    private fun createGetMovieCall()  = OkHttpClient().newCall(createGetMovieRequest())
//
//    private fun createGetMovieRequest() = Request.Builder()
//        //.url("https://api.themoviedb.org/3/movie/movie_id?language=en-US")
//        .url("https://api.themoviedb.org/3/movie/3?language=en-US")
//        .get()
//        .addHeader("accept", "application/json")
//        .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0MWMwMTU1YjBjNDY3MTJiYmNiYzc2NTBlYTg1MjQ5NCIsInN1YiI6IjY1YTBmMGJkZDIwN2YzMDEyMmU3NWE1NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.G60ugoN4XgUfXDjl_KNB-0TbQr0Yg2VLwq1eb3K43E4")
//        .build()


}