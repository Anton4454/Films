package com.example.films

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class SplashActivity : AppCompatActivity() {
    private lateinit var lottieAnimationView: LottieAnimationView

    private lateinit var request: okhttp3.Request
    private lateinit var jsonFilmsResponse: okhttp3.Response
    private var mHandler = Handler()
    private val gson = Gson()
    private var jsonFilms: TopRatedMovies? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        val thread = Thread {
            try {
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

                GlobalScope.launch {
                    jsonFilmsResponse = client.newCall(request!!).execute()
                    mHandler.postDelayed(initResponse, 100)
                }
            } catch (e: Exception) {
                Log.e("Error", e.message)
            }
        }

        thread.start()
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
            jsonFilms = gson.fromJson(jsonFilmsResponse.toString(), TopRatedMovies::class.java)
            mHandler.postDelayed(parseIsReady, 100)
        }
    }

    private val initResponse: Runnable = object : Runnable {
        override fun run() {
            if (jsonFilmsResponse == null) {
                mHandler.postDelayed(this, 100)
            } else {
                downloadByUrl()
            }
        }
    }

    private val parseIsReady: Runnable = object : Runnable {
        override fun run() {
            if (jsonFilms == null) {
                mHandler.postDelayed(this, 100)
            } else {
                Toast.makeText(this@SplashActivity, jsonFilms!!.id.toString(), Toast.LENGTH_LONG).show()
                startActivity(
                    Intent(
                        this@SplashActivity, MainActivity::class.java
                    )
                )
            }
        }
    }
}