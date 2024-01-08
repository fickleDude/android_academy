package com.android.android_academy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), PosterListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, FragmentMoviesList())
            .commit()
    }

    override fun onButtonClickedSwitchToMovieDetails() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, FragmentMoviesDetails())
            .commit()
    }
}