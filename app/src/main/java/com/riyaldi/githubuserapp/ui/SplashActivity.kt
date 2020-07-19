package com.riyaldi.githubuserapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.riyaldi.githubuserapp.R
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val time = 2500
        val homeIntent = Intent(this@SplashActivity, MainActivity::class.java)
        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        }, time.toLong())

    }
}