package com.example.cardeals

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cardeals.databinding.ActivityPostCarsBinding
import com.example.cardeals.models.InsertCar
import com.example.cardeals.models.passUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_post_cars.*
import java.util.*

class PostCars : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    //image
    private lateinit var  auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage

    lateinit var imageUri: Uri
    private lateinit var progressdialog: ProgressDialog
    private lateinit var binding: ActivityPostCarsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostCarsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //back actionbar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //progressDialog
        progressdialog = ProgressDialog(this)
        progressdialog.setTitle("Please wait")
        progressdialog.setMessage("Posting...")
        progressdialog.setCanceledOnTouchOutside(false)

        var imgurl: String
        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()


        btnselect.setOnClickListener {
            // select the picture from the images
            getImage()


        }




        binding.btnpost.setOnClickListener {
            progressdialog.show()

    uploadData()
}




    }
    private fun uploadData() {

        //uploading the image
        val reference=storage.reference.child("Cars").child(Date().time.toString())
        reference.putFile(imageUri).addOnCompleteListener{
            if(it.isSuccessful){
                reference.downloadUrl.addOnSuccessListener { task ->
                    uploadInfo(task.toString())
                }


            }

        }.addOnFailureListener{
            progressdialog.dismiss()
            Toast.makeText(this, "No Image selected ", Toast.LENGTH_SHORT).show()
        }


    }

    private fun uploadInfo(imgUrl: String) {
        //pass Id
        val myPreference = passCount(this)
        var count= myPreference.getOrderCount()
        count++
        myPreference.setOrderCount(count)

        //pass owner
        var get_owner=passUser(this)
        var owner=get_owner.getUser()

        //update the info into firebase fields
        val inserter= InsertCar(etmake.text.toString(), etmodel.text.toString(), etkm.text.toString()
        , etwheels.text.toString(), etcolor.text.toString(), etinterior.text.toString(), etgear.text.toString(),
        etseats.text.toString(), etcylinders.text.toString(), etairbags.text.toString(),
            etdescribe.text.toString(),imgUrl,owner, count.toString(),
            etprice.text.toString(),        etengine.text.toString())

            database.reference.child("Cars")

                .child(count.toString()).setValue(inserter).addOnSuccessListener {

                    progressdialog.dismiss()
                    Toast.makeText(this, "The Car has been successfully Posted and is non visible to potential Buyers !", Toast.LENGTH_SHORT).show()
               startActivity(Intent(this, Home::class.java));
                }
         }

    private fun getImage() {
        val intent = Intent()
        intent.type="image/*"
        intent.action= Intent.ACTION_GET_CONTENT


        startActivityForResult(intent, 100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data !=null){
            if(data.data != null){
                imageUri=data.data!!
                binding.image.setImageURI(imageUri)
            }


        }
    }
}