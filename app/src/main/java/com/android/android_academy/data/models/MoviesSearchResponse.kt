package com.android.android_academy.data.models

import kotlinx.serialization.SerialName

//popular movies list request
//path https://api.themoviedb.org/3/movie/popular?api_key=${api_key}
//JSON example
//{
//    "page": 1,
//    "results": [
//    {
//        "id": 75780,
//        "overview": "One morning in an ordinary town, five people are shot dead in a seemingly random attack. All evidence points to a single suspect: an ex-military sniper who is quickly brought into custody. The interrogation yields one written note: 'Get Jack Reacher!'. Reacher, an enigmatic ex-Army investigator, believes the authorities have the right man but agrees to help the sniper's defense attorney. However, the more Reacher delves into the case, the less clear-cut it appears. So begins an extraordinary chase for the truth, pitting Jack Reacher against an unexpected enemy, with a skill for violence and a secret to keep.",
//        "poster_path": "/uQBbjrLVsUibWxNDGA4Czzo8lwz.jpg",
//        "release_date": "2012-12-20",
//        "vote_average": 6.618,
//        "title": "Jack Reacher",
//    },
//    {...},
//    {...}
//    ],
//    "total_pages": 1,
//    "total_results": 3
//}
class MoviesSearchResponse(
    @SerialName("total_results")
    private var count : Int,
    @SerialName("results")
    private var movies : List<MovieModel>
) {

    fun getMovies():List<MovieModel>{
        return movies
    }


}