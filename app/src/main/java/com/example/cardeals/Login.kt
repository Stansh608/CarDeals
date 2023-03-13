package com.example.cardeals

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.cardeals.databinding.ActivityLoginBinding
import com.example.cardeals.models.passUser

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Login : AppCompatActivity() {
    // progress dialog
    private lateinit var progressdialog: ProgressDialog
    //binding
    private lateinit var binding: ActivityLoginBinding

    // Database Reference
    private lateinit var dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //create a progress dialog
        progressdialog= ProgressDialog(this)
        progressdialog.setTitle("Authenticating")
        progressdialog.setMessage("Please wait...")
        progressdialog.setCanceledOnTouchOutside(false)

//forgot pass
        binding.forgotpass.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }


        binding.loginbtn.setOnClickListener {


            //Get the user details
            var username= binding.lusername.text.toString().trim()
            var password= binding.lpassword.text.toString().trim()
            //all fields not empty
            if (username.isEmpty()) {

                binding.lusername.error = "Please enter your Username"

            } else if (password.isEmpty()) {
                binding.lpassword.error = "Please enter your Password"

            }else{
                //show progress dialog
                progressdialog.show()

                // validate details
                dbref= FirebaseDatabase.getInstance().getReference("Register")
                dbref.child(username).get().addOnSuccessListener {
                    if (it.exists()){
                        var pass=it.child("pass").value.toString()
                        if (pass==password){
                            var putUser=passUser(this)
                            putUser.setUser(username)
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                            progressdialog.dismiss()
                            startActivity(Intent(this, Home::class.java))
                            finish()
                        }
                        else{
                            progressdialog.dismiss()
                            Toast.makeText(this, "Wrong Password!", Toast.LENGTH_LONG).show()
                        }
                    }else {
                        progressdialog.dismiss()
                        Toast.makeText(this, "Account with these details doesn't Exist", Toast.LENGTH_LONG).show()

                    }
                }


            }
        }


        findViewById<TextView>(R.id.newAccount).setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
        }
        supportActionBar?.hide()
    }
}