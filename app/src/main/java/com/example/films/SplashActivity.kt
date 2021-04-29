package com.example.films

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import kotlinx.coroutines.GlobalScope
import android.util.Log
import com.google.gson.Gson
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL

class SplashActivity : AppCompatActivity() {
    private lateinit var lottieAnimationView: LottieAnimationView
    private var jsonFilmsString: okhttp3.Response? = null
    private var mHandler = Handler()
    private lateinit var jsonFilms: TopRatedMovies
    private var request: Request? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        downloadByUrl()

        /*startActivity(
                  Intent(
                      this@SplashActivity, MainActivity::class.java
                  )
              )*/
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
            val client = OkHttpClient()
            request = Request.Builder()
                .url("https://imdb8.p.rapidapi.com/title/get-top-rated-movies")
                .get()
                .addHeader(
                    "x-rapidapi-key",
                    "2c70749e73mshbb648cb440852a1p1f76fbjsncc89d40cc767"
                )
                .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .build()



            val gson = Gson()
            jsonFilmsString = client.newCall(request!!).execute()
            jsonFilms = gson.fromJson(jsonFilmsString.toString(), TopRatedMovies::class.java)
            mHandler.postDelayed(parseIsReady, 0)
        }
    }

    private val parseIsReady: Runnable = object : Runnable {
        override fun run() {
        }
    }
}