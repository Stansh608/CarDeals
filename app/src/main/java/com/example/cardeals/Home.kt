package com.example.cardeals

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardeals.adapters.ListCars
import com.example.cardeals.databinding.ActivityHomeBinding
import com.example.cardeals.models.CarData
import com.example.cardeals.models.passUser
import com.google.firebase.database.*

class Home : AppCompatActivity() {
    //dialog
    private lateinit var progressdialog: ProgressDialog

    //binding
    private lateinit var binding: ActivityHomeBinding

    //dbref
    private lateinit var dbref:DatabaseReference
    //arraylist
    private lateinit var cardetails:ArrayList<CarData>
    private lateinit var caradapter: ListCars


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //put user
        var get_user= passUser(this)
        binding.txtUsername.text= "Welcome " + get_user.getUser().toString()


        //profile
        binding.imgprofile2.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))
        }


        // initializing
        cardetails=ArrayList()
        caradapter= ListCars(this, cardetails)
        binding.recyclerCar.layoutManager = LinearLayoutManager(this)
        binding.recyclerCar.setHasFixedSize(true)
        binding.recyclerCar.adapter=caradapter

        getCarDetails()


        //
        binding.txtSellcar.setOnClickListener {
            startActivity(Intent(this, PostCars::class.java))
        }




    }

    private fun getCarDetails() {
        //
        dbref=FirebaseDatabase.getInstance().getReference("Cars")
        dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                // check if there's data

                if(snapshot.exists()){

                    for (carSnapshot in snapshot.children){
                        var cars= carSnapshot.getValue(CarData::class.java)
                        cardetails.add(cars!!)

                    }
                    binding.recyclerCar.adapter=caradapter

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Home, "No Data!!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}