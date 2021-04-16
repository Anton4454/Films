package com.example.films.ui.FilmPage

import android.R.attr.packageNames
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.films.R
import com.example.films.ResultsItem
import com.jgabrielfreitas.core.BlurImageView
import kotlinx.android.synthetic.main.fragment_film_page.view.*


class FilmPageFragment : Fragment() {

    private lateinit var root: View
    private lateinit var jsonFilm: ResultsItem
    private lateinit var fragmentBack: BlurImageView
    private lateinit var filmPhoto: ImageView
    private lateinit var title: TextView
    private var position: Int = 0

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_film_page, container, false)
        fragmentBack = root.fragment_back
        filmPhoto = root.filmPhoto
        title = root.title
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

        Glide
            .with(requireContext())
            .load(
                "https://themoviedb.org/t/p/w600_and_h900_face" + jsonFilm.posterPath
            )
            .override(400, 600)
            .into(filmPhoto)

        title.text = jsonFilm.title
        /*root.btn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }*/

        fragmentBack.setBlur(4)
        return root
    }
}