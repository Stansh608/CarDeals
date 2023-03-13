package com.example.cardeals.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cardeals.CarDetails
import com.example.cardeals.R
import com.example.cardeals.databinding.CarlistBinding
import com.example.cardeals.models.CarData

class ListCars (
    //context
    var c: Context, var carlist:ArrayList<CarData>
): RecyclerView.Adapter<ListCars.listCarsHolder>(){

    inner class listCarsHolder(var v:CarlistBinding): RecyclerView.ViewHolder(v.root){}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listCarsHolder {
        //
        var inflater= LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<CarlistBinding>(inflater,  R.layout.carlist, parent, false)
        return listCarsHolder(v)
    }
    override fun onBindViewHolder(holder: listCarsHolder, position: Int) {
        holder.v.isCar = carlist[position]
        // get the data for cars
        val newList=carlist[position]
        val make=newList.make
        val price= newList.price
        val image=newList.image
        val owner= newList.owner
        val description= newList.description
        val cylinders =newList.cylinders
        val id=newList.id
        val year=newList.year
        val kilometres=newList.kilometres
        val wheels =newList.wheels
        val color =newList.color
        val interior= newList.interior
        val gear= newList.gear
        val seats=newList.seats
        val model=newList.model
        val engine=newList.engine
        val airbags=newList.airbags






        holder.v.root.setOnClickListener{
            val mIntent = Intent(c, CarDetails::class.java)

            mIntent.putExtra("cylinders",cylinders)
            mIntent.putExtra("make",make)
            mIntent.putExtra("image",image)
            mIntent.putExtra("owner",owner)
            mIntent.putExtra("id",id)
            mIntent.putExtra("airbags",airbags)
            mIntent.putExtra("description",description)
            mIntent.putExtra("year",year)
            mIntent.putExtra("kilometres",kilometres)
            mIntent.putExtra("wheels",wheels)
            mIntent.putExtra("color",color)
            mIntent.putExtra("interior",interior)
            mIntent.putExtra("gear",gear)
            mIntent.putExtra("seats",seats)
            mIntent.putExtra("price",price)
            mIntent.putExtra("engine", engine)
            mIntent.putExtra("model", model)


            c.startActivity(mIntent)

        }


    }

    override fun getItemCount(): Int {
        return carlist.size
    }

}