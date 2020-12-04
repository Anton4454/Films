package com.example.films

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class SplashActivity : AppCompatActivity() {
    private var mHandler = Handler()
    private lateinit var lottieAnimationView:LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lottieAnimationView = findViewById(R.id.lottie_splash)
        Handler().postDelayed({
            startActivity(
                Intent(
                    this@SplashActivity, MainActivity::class.java
                )
            )
            finish()
        }, 2500)
    }
}