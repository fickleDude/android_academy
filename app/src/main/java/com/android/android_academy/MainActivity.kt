package com.android.android_academy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), PosterListener {
//    private var moviesList: FragmentMoviesList? = null
//    private var moviesDetails: FragmentMoviesDetails? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragments_container, FragmentMoviesListObserver())
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
                .replace(R.id.fragments_container, FragmentMoviesDetails())
                .commit()
    }

    companion object {
        const val MOVIES_LIST_TAG = "MoviesList"
       // const val MOVIES_DETAILS_TAG = "MoviesDetails"
    }
}