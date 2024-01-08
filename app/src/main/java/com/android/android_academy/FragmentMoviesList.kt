package com.android.android_academy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragmentMoviesList : Fragment() {
    private var posterFragmentClickListener: PosterListener? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        view?.findViewById<View>(R.id.poster)?.apply {
            setOnClickListener{
                posterFragmentClickListener?.onButtonClickedSwitchToMovieDetails()
            }
        }
        return view

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PosterListener){
            posterFragmentClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        posterFragmentClickListener = null
    }

}