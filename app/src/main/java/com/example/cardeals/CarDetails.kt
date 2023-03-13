package com.example.cardeals

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cardeals.databinding.ActivityCarDetailsBinding
import com.example.cardeals.load_image.getProgressDrawable
import com.example.cardeals.load_image.loadImage
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import java.math.BigDecimal

class CarDetails : AppCompatActivity() {
    //paypal
    var config:PayPalConfiguration?=null
    var amount: Double =0.00

    private lateinit var binding: ActivityCarDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCarDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //arrow
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // receive intentbundle and display
        val carintent=intent
        binding.tvmake.text=carintent.getStringExtra("make")
        binding.tvyear.text=carintent.getStringExtra("year") + " model"

        binding.tvkm.text = carintent.getStringExtra("kilometres")
        binding.tvprice.text="Price: $" + carintent.getStringExtra("price")
        binding.tvcolor.text = carintent.getStringExtra("color")
        binding.tvinterior.text = carintent.getStringExtra("interior")
        binding.tvgear.text = carintent.getStringExtra("gear")
        binding.tvseats.text = carintent.getStringExtra("seats")
        binding.tvcylinders.text = carintent.getStringExtra("cylinders")
        binding.tvwheels.text = carintent.getStringExtra("wheels")
        binding.tvengine.text = carintent.getStringExtra("engine")
        binding.tvdescription.text = carintent.getStringExtra("description")
        binding.tvairbags.text = carintent.getStringExtra("airbags")

        //load image
        val imageurl=carintent.getStringExtra("image")

        binding.image.loadImage(imageurl, getProgressDrawable(this))


// paypal seervice

        config= PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId("AYLQcg207BprfDSUJSr5ccdpPHJeuyeeR1QhbVoZ5STB69ENAPFzwmsipQZdUx46orEk6IqGAImVhwlv")

        var i=Intent(this, PayPalService::class.java)
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        startService(i)


        binding.btnBuy.setOnClickListener {

            amount=carintent.getStringExtra("price").toString().toDouble()

            var payment = PayPalPayment(BigDecimal.valueOf(amount), "USD", "Car Deals App", PayPalPayment.PAYMENT_INTENT_SALE)
            var intent = Intent(this, PaymentActivity::class.java)

            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config)
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment)
            startActivityForResult(intent, 100)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==100){
            if(requestCode== Activity.RESULT_OK){
                startActivity(Intent(this, ConfirmPayment::class.java))
                finish()

            }
        }
    }

    //stop paypal service
    override fun onDestroy() {
        stopService(Intent(this, PayPalService::class.java))
        super.onDestroy()
    }
}