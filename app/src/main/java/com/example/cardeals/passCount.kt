package com.example.cardeals

import android.content.Context

class passCount (context: Context) {
    val Preference_name=" SharedReferenceVal"
    val preference_count="Order_count"

    val preference= context.getSharedPreferences(Preference_name, Context.MODE_PRIVATE)
    fun getOrderCount(): Int{
        return preference.getInt(preference_count, 1)
    }
    //set value
    fun setOrderCount(count:Int){
        val editor= preference.edit()
        editor.putInt(preference_count, count)
        editor.apply()
    }
}