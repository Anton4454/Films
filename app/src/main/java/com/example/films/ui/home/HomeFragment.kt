package com.example.films.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.films.*
import com.example.films.ui.ClickListener.RecyclerItemClickListener
import com.example.films.ui.FilmPageActivity.FilmPageActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_films.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

class HomeFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FilmAdapter
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var jsonFilms: Response
    private var mHandler = Handler()
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_films, container, false)
        recyclerView = root.findViewById(R.id.recyclerView)
        downloadByUrl()
        return root
    }

    private fun createRecyclerView(adapter: FilmAdapter) {
        linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(context, 1)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter

        /*recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                this.context,
                recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        val showPhotoIntent =
                            Intent(this@HomeFragment, FilmPageActivity::class.java)
                        showPhotoIntent.putExtra(
                            FilmAdapter.PhotoHolder.FILM_KEY,
                            jsonFilms?.results?.get(position))
                        )

                        val photoPageActivity = FilmPageActivity()
                        val pair = AndroidPair<View, String>(
                            view?.findViewById(R.id.album),
                            photoPageActivity.VIEW_NAME_HEADER_IMAGE
                        )

                        val activityOptions: ActivityOptionsCompat =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                this@MainActivity,
                                pair
                            )

                        ActivityCompat.startActivity(
                            this@HomeFragment,
                            showPhotoIntent,
                            activityOptions.toBundle()
                        )
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                        TODO("Not yet implemented")
                    }

                })
        )*/
    }

    private fun downloadByUrl() {
        GlobalScope.launch {
            var response: String =
                URL("https://api.themoviedb.org/3/movie/now_playing?api_key=d866b8cb9d02a5fc365da1327bc3f464&language=ru&page=1")
                    .readText()
            val gson = Gson()
            jsonFilms = gson.fromJson(response, Response::class.java)
            mHandler.postDelayed(parseIsReady, 0)
        }
    }

    private val parseIsReady: Runnable = object : Runnable {
        override fun run() {
            if (jsonFilms.results?.size!! < 20) {
                mHandler.postDelayed(this, 100)
            } else {
                adapter = FilmAdapter(jsonFilms)
                createRecyclerView(adapter)
            }
        }
    }
}