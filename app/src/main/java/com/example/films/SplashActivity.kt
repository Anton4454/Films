package com.example.films

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

class SplashActivity : AppCompatActivity() {
    private var mHandler = Handler()
    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var jsonFilms: Response
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lottieAnimationView = findViewById(R.id.lottie_splash)
        downloadByUrl()
    }

    private fun downloadByUrl() {
        GlobalScope.launch {
            var response: String =
                URL("https://api.themoviedb.org/3/movie/popular?api_key=d866b8cb9d02a5fc365da1327bc3f464&language=ru&page=1")
                    .readText()
            val gson = Gson()
            jsonFilms = gson.fromJson(response, Response::class.java)
            mHandler.postDelayed(parseIsReady, 0)
        }
    }

    private val parseIsReady: Runnable = object : Runnable {
        override fun run() {
            if (jsonFilms.results?.size != 20) {
                mHandler.postDelayed(this, 100)
            } else {
                val intent = Intent(baseContext, MainActivity::class.java)
                intent.putExtra("response_list", jsonFilms)
                startActivity(intent)
                overridePendingTransition(
                    R.transition.fade_in_transition,
                    R.transition.fade_out_transition
                )
            }
        }
    }
}