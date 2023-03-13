package com.example.cardeals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide() // hide action bar for splash screen

        val handler= Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(Intent(this,LandingPage::class.java)) //Move to login
            finish()
        }, 2000) //after 1seconds

    }
}