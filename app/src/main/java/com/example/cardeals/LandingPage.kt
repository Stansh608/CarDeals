package com.example.cardeals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cardeals.databinding.ActivityLandingPageBinding

class LandingPage : AppCompatActivity() {
    private lateinit var binding: ActivityLandingPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.signon.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
        }
        binding.logon.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

    }
}