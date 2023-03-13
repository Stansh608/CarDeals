package com.example.cardeals

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cardeals.databinding.ActivityForgotPasswordBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ForgotPassword : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var pd:ProgressDialog
    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //config pd
        pd=ProgressDialog(this)
        pd.setTitle("Please Wait ...")
        pd.setMessage("Processing")
        pd.setCanceledOnTouchOutside(false)
        //take
        binding.resetbtn.setOnClickListener {
            pd.show()
            var username=binding.username.text.toString().trim()
            var email=binding.email.text.toString().trim()
            if ((username.isEmpty()) || (email.isEmpty())){
                pd.dismiss()
                binding.username.error="This field cannot be empty"
                binding.email.error="This field cannot be empty"
            }

            dbref=FirebaseDatabase.getInstance().getReference("Register")
            dbref.child(username).get().addOnSuccessListener {
                if (it.exists())
                {
                    var ema=it.child("email").value.toString()
                    if (email==ema){

                        var adp= mapOf(
                            "pass" to ema
                        )
                        dbref.child(username).updateChildren(adp).addOnSuccessListener {
                            pd.dismiss()
                            Toast.makeText(this, "The password has been reset to your Email. Use email as password", Toast.LENGTH_LONG).show()

                        }
                    } else{
                        pd.dismiss()
                        Toast.makeText(this, "Invalid or Wrong Email! ", Toast.LENGTH_SHORT).show()
                    }

                } else{
                    pd.dismiss()
                    Toast.makeText(this, "404:There is no account with such Details!!!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}