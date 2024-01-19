//package com.android.android_academy.UI.listMovies
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.RecyclerView
//import com.android.android_academy.R
//import com.android.android_academy.UI.list.MoviesListViewModel
//import com.android.android_academy.data.models.MovieModel
//import com.android.android_academy.data.models.MoviesSearchResponse
//import com.android.android_academy.remote.Credentials
//import com.android.android_academy.remote.MovieApi
//import com.android.android_academy.remote.NetworkModule
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.io.IOException
//
//
//class MoviesListFragment : Fragment(), Observer<List<MovieModel>> {
//
//    private var recycler: RecyclerView? = null //holds each movie in the list
//
//    private lateinit var viewModel: MoviesListViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        //intialize ViewModel
//        viewModel = ViewModelProvider(this)[MoviesListViewModel::class.java]
//        //intialize fragment with xml file
//        return inflater.inflate(R.layout.fragment_movies_list, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        recycler = view.findViewById(R.id.moviesList) //intialize RecyclerView with xml component
//        //intialize adapter for RecyclerView
//        //adapter will tell how to interpret stored data on each item of RecyclerView
//        val adapter = MovieAdapter(view.context)
//        recycler?.adapter = adapter
//    }
//
//    override fun onStart() {
//        super.onStart()
//        //add observer to update UI if data changes
//        //first arg - observe cycle of fragment
//        //second arg - use method onChanged of this class
//        viewModel.getMovies().observe(this, this)
//    }
//
////    private fun updateData() {
////        (recycler?.adapter as? MovieAdapter)?.apply {
////            bindMovies(getMovieSearchList("Blade Runner"))
////        }
////    }
//
//    //implement observer method to update data
//    override fun onChanged(value: List<MovieModel>) {
////        updateData()
//    }
//
//    private fun getMovieSearchList(title : String) : List<MovieModel>{
//        val movieApi : MovieApi = NetworkModule.getMovieApi()
//        val responseQueue : Call<MoviesSearchResponse> = movieApi
//            .searchForMoviesByTitle(
//                Credentials().getApiKey(),
//                title
//            )
//        Log.v("RETROFIT", "Retrofit request " + responseQueue.request().url)
//        var result : List<MovieModel> = emptyList()
//        responseQueue.enqueue(object : Callback<MoviesSearchResponse> {
//            override fun onResponse(
//                call: Call<MoviesSearchResponse>,
//                response: Response<MoviesSearchResponse>
//            ) {
//                if(response.code() == 200){
//                    // Log.v("RETROFIT", "Retrofit response " + response.body()?.getMovies().toString())
//                    Log.v("RETROFIT", "Retrofit response " + response.body().toString())
//                    val moviesSearch : MoviesSearchResponse = response.body()!!
//
//                    result = response.body()?.getMovies() ?: emptyList()
//
//                    for(movie : MovieModel in result){
//                        Log.v("RETROFIT", ("Movie Model ${movie.toString()}"))
//                    }
//                }else{
//                    try{
//                        Log.v("RETROFIT", "Retrofit error response " + response.message())
//                    }catch (e: IOException){
//                        e.printStackTrace()
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<MoviesSearchResponse>, t: Throwable) {
//                Log.v("RETROFIT", "Retrofit on failure response"+t.stackTraceToString())
//                t.printStackTrace()
//            }
//
//        })
//        return result
//    }
//
//}