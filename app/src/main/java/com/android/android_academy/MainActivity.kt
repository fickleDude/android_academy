package com.android.android_academy

import com.android.android_academy.network.Credentials
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.android_academy.UI.listMovies.MoviesListFragment
import com.android.android_academy.UI.listMovies.PosterListener
import com.android.android_academy.UI.movieDetail.MovieDetailsFragment
import com.android.android_academy.data.models.MovieModel
import com.android.android_academy.data.models.MoviesSearchResponse
import com.android.android_academy.network.MovieApi
import com.android.android_academy.network.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainActivity : AppCompatActivity(), PosterListener{
//    private var moviesList: FragmentMoviesList? = null
//    private var moviesDetails: FragmentMoviesDetails? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragments_container, MoviesListFragment())
            .commit()

//        if (savedInstanceState == null) {
//            moviesList = FragmentMoviesList()
//            moviesList?.apply {
//                supportFragmentManager.beginTransaction()
//                .add(R.id.fragments_container, this, MOVIES_LIST_TAG)
//                .commit() }
//
//        } else {
//            moviesList  =
//                supportFragmentManager.findFragmentByTag(MOVIES_LIST_TAG) as? FragmentMoviesList
//        }

    }

    override fun onPosterClickedSwitchToMovieDetails() {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragments_container, MovieDetailsFragment())
                .commit()
    }

    companion object {
        const val MOVIES_LIST_TAG = "MoviesList"
       // const val MOVIES_DETAILS_TAG = "MoviesDetails"
    }

}