package com.example.films

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.films.ui.home.HomeFragment
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
        setContentView(R.layout.splash)

        lottieAnimationView = findViewById(R.id.lottie_splash)
        Handler().postDelayed({
            startActivity(
                Intent(
                    this@SplashActivity, MainActivity::class.java
                )
            )

            overridePendingTransition(
                R.transition.fade_in_transition,
                R.transition.fade_out_transition
            )

            finish()
        }, 2500)
    }

    private fun downloadByUrl() {
        GlobalScope.launch {
            val response: String =
                URL("https://api.themoviedb.org/3/movie/popular?api_key=d866b8cb9d02a5fc365da1327bc3f464&language=en&page=1")
                    .readText()
            val gson = Gson()
            jsonFilms = gson.fromJson(response, Response::class.java)
            mHandler.postDelayed(parseIsReady, 0)
        }
    }


}