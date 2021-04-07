package com.example.films.ui.FilmPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.films.R
import com.example.films.ui.home.HomeFragment

class FilmPageFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_film_page, container, false)
        /*root.btn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }*/
        return root
    }
}