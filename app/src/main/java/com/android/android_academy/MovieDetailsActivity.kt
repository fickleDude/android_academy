package com.android.android_academy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.android_academy.UI.MoviesListViewModel
import com.android.android_academy.data.models.MovieDetailsModel
import com.android.android_academy.data.models.MovieModel
import com.bumptech.glide.Glide

class MovieDetailsActivity : AppCompatActivity() {
    //widgets
    private lateinit var title : TextView
    private lateinit var plot : TextView
    private lateinit var image : ImageView
    private lateinit var rating : RatingBar

    //ViewModel
    private lateinit var viewModel: MoviesListViewModel

    private lateinit var movieDetails: MovieDetailsModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details_item)

        //initialize widgets
        title = findViewById(R.id.movie_title_details)
        plot = findViewById(R.id.movie_plot_details)
        image = findViewById(R.id.movie_image_details)
        rating = findViewById(R.id.movie_ratingBar_details)

        //initialize view model
        viewModel = ViewModelProvider(this)[MoviesListViewModel::class.java]

        //set value to widgets
        val id = getDataFromIntent()
        if(id != null){
            viewModel.searchByIdMovieApi(id)
        }
        observeChanges()
    }


//    intent - механизм для описания одной операции
//    применение - запуск новой активности,  трансляции сообщений по системе
//    можно передавать данные - intent.putExtra("FILES_LIST", fileList); getIntent().getSerializableExtra("FILES_LIST")

    private fun getDataFromIntent() : String?{
        if(intent.hasExtra("movie")){
            return intent.getParcelableExtra<MovieModel?>("movie")!!.getId()
        }
        return null
    }

    private fun observeChanges() {
        viewModel.getMovieDetails().observe(this, object : Observer<MovieDetailsModel?> {
            override fun onChanged(value: MovieDetailsModel?) {
                if (value != null) {
                    //bind widgets
                    title.text = value.Title
                    plot.text = value.Plot

                    rating.numStars = value.imdbRating!!.toDouble().toInt()

                    Glide.with(baseContext).load(value.Poster).into(image)
                }
            }

        })
    }
}