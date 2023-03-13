package com.example.cardeals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cardeals.databinding.ActivityConfirmPaymentBinding

class ConfirmPayment : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=ActivityConfirmPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}