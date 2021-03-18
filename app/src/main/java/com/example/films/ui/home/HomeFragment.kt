package com.example.films.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.films.FilmAdapter
import com.example.films.R
import com.example.films.Response
import com.example.films.ui.ClickListener.RecyclerItemClickListener
import com.example.films.ui.FilmPage.FilmPageFragment
import com.google.gson.Gson
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter
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
    private lateinit var root: View
    private var mHandler = Handler()
    private val fragment: Fragment = FilmPageFragment()

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
        recyclerView.adapter = SlideInRightAnimationAdapter(adapter).apply {
            // Change the durations.
            setDuration(700)
            // Change the interpolator.
            setInterpolator(OvershootInterpolator())
            // Disable the first scroll mode.
            setFirstOnly(false)
        }


        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                context,
                recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        activity!!.supportFragmentManager.commit {
                            setCustomAnimations(
                                R.anim.slide_in,
                                R.anim.fade_out,
                                R.anim.fade_in,
                                R.anim.slide_out
                            )
                            replace(R.id.nav_host_fragment, fragment)
                            addToBackStack(null)
                        }
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                    }
                })
        )
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

    /*private fun layoutAnimation(recyclerView: RecyclerView) {
        var context = recyclerView.context
        var layoutAnimationController =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_slide_right)
        recyclerView.layoutAnimation = layoutAnimationController
        recyclerView.adapter?.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }*/

    private val parseIsReady: Runnable = object : Runnable {
        override fun run() {
            if (jsonFilms.results?.size!! < 20) {
                mHandler.postDelayed(this, 100)
            } else {
                adapter = FilmAdapter(jsonFilms)
                Toast.makeText(context, jsonFilms?.totalResults.toString(), Toast.LENGTH_SHORT).show()
                createRecyclerView(adapter)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.supportFragmentManager?.commit {
            remove(fragment)
        }
    }
}