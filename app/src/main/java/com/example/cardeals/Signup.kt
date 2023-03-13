package com.example.cardeals

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.ProgressBar
import android.widget.Toast
import com.example.cardeals.databinding.ActivitySignupBinding
import com.example.cardeals.models.Register
import com.example.cardeals.models.passUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {
    // progressdialog
    private lateinit var progressdialog:ProgressDialog

    //dbref
    private lateinit var dbref: DatabaseReference

    //binding
    private lateinit var binding:ActivitySignupBinding
    //authent
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //no action bar
        supportActionBar?.hide()

        ///instance
        mAuth=FirebaseAuth.getInstance()

        progressdialog= ProgressDialog(this)
        progressdialog.setTitle("Creating Account")
        progressdialog.setMessage("Please wait....")
        progressdialog.setCanceledOnTouchOutside(false)

        binding.signupbtn.setOnClickListener {

            //get the data
            var username= binding.username.text.toString().trim()
            var names=binding.names.text.toString().trim()
            var email = binding.email.text.toString().trim()
            var pass1=binding.password.text.toString().trim()
            var pass2 = binding.cpassword.text.toString().trim()

            //empty fields alert
            if(username.isEmpty()){
                binding.username.error="This field cannot be empty"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.email.error = "Please enter a valid email"
            } else if (pass1.isEmpty()){
                binding.password.error = "This field cannot be empty"
            } else if (names.isEmpty()) {
                binding.names.error = "This field cannot be empty"
            }else if (pass2.isEmpty()){
                binding.cpassword.error = "Confirm your password"
            } else if (pass1 != pass2){
                binding.cpassword.error = " The password does not match"
                Toast.makeText(this, "Password Mismatch!!", Toast.LENGTH_SHORT).show()
            } else{
                progressdialog.show()
                //var userid=mAuth.currentUser?.uid!!
                // check if username exists
                dbref=FirebaseDatabase.getInstance().getReference("Register")
                dbref.child(username).get().addOnSuccessListener {
                    if (it.exists()){

                            binding.username.error = " The username exists"
                            Toast.makeText(this, "The username is already taken. Try another one.", Toast.LENGTH_LONG).show()

                        progressdialog.dismiss()
                        } else{
                        val userData=Register(username,names,email, pass1)
                        dbref.child(username).setValue(userData).addOnSuccessListener {
                            Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                            progressdialog.dismiss()
                            var setUser=passUser(this)
                            setUser.setUser(username)
                            startActivity(Intent(this, Home::class.java))
                            finish()
                        }
                    }
                }
            }
        }

    }
}