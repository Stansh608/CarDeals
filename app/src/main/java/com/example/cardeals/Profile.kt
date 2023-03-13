package com.example.cardeals

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cardeals.databinding.ActivityProfileBinding
import com.example.cardeals.models.passUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.delay

class Profile : AppCompatActivity() {
    private lateinit var progress: ProgressDialog
    private lateinit var binding: ActivityProfileBinding
    private lateinit var dbref:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // get user
        var user_name=passUser(this)
        var user=user_name.getUser()

        //progress dialog
        progress=ProgressDialog(this)
        progress.setTitle("Please wait ")
        progress.setMessage("Updating")
        progress.setCanceledOnTouchOutside(false)

        dbref=FirebaseDatabase.getInstance().getReference("Register")
        dbref.child(user).get().addOnSuccessListener {
            if (it.exists())
            {
                val username=it.child("username").value.toString()
                val fname=it.child("names").value.toString()
                val email=it.child("email").value.toString()

                binding.etusername.text=username
                binding.etnames.text=fname
                binding.etemail.text=email

            }
        }

        btnupdate.setOnClickListener {

            // get to validate inputs
            var pass1= binding.etnpass.text.toString().trim()
            var pass2= binding.etcpass.text.toString().trim()
            if (pass1.isEmpty()){
                binding.etnpass.error="Enter a new Password"
            } else if(pass1 != pass2){
                binding.etcpass.error= "Password doesn't Match"
            } else{
                progress.show()
                dbref=FirebaseDatabase.getInstance().getReference("Register")
                var adp= mapOf(
                    "pass" to pass1
                )
                dbref.child(user).updateChildren(adp).addOnSuccessListener {
                    progress.dismiss()
                    Toast.makeText(this, "You have successfully changed your Password", Toast.LENGTH_LONG, ).show()
                    startActivity(Intent(this,Login::class.java))
                    finish()
                }
            }

        }
    }
}