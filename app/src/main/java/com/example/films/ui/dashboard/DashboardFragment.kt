package com.example.films.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.films.R
import com.example.films.ui.FilmPage.FilmPageFragment

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        requireActivity().supportFragmentManager.commit {
            remove(FilmPageFragment())
        }
        val root = inflater.inflate(R.layout.fragment_wanna_watch, container, false)
        return root
    }
}