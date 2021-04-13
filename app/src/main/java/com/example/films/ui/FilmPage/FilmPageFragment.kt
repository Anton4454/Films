package com.example.films.ui.FilmPage

import android.R.attr.packageNames
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.films.R
import com.example.films.ResultsItem
import kotlinx.android.synthetic.main.fragment_film_page.view.*


class FilmPageFragment : Fragment() {

    protected lateinit var root: View
    protected lateinit var jsonFilm: ResultsItem
    protected lateinit var fragmentBack: ImageView
    protected var position: Int = 0

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_film_page, container, false)
        fragmentBack = root.fragment_back
        val bundle = this.arguments
        if (bundle != null) {
            jsonFilm = bundle.getParcelable<ResultsItem>("films")!!
            position = bundle.getInt("position")
        }

        /*val id: Int = resources.getIdentifier(
            "https://themoviedb.org/t/p/w600_and_h900_face" + jsonFilm.posterPath,
            "drawable",
            packageNames.toString()
        )*/

        Glide
            .with(requireContext())
            .load(
                "https://themoviedb.org/t/p/w600_and_h900_face" + jsonFilm.posterPath
            )
            .override(400, 600)
            .into(fragmentBack)
        /*root.btn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }*/

        return root
    }
}