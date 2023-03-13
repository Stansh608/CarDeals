package com.example.cardeals.models

import android.content.Context

class passUser(context : Context) {
    val Preference_name=" Shared"
    val preference_count="user"
    val stan="stan"

    val preference= context.getSharedPreferences(Preference_name, Context.MODE_PRIVATE)
    fun getUser(): String{
        return preference.getString(preference_count, stan)!!
    }
    //set value
    fun setUser(count: String){
        val editor= preference.edit()
        editor.putString(preference_count, count)
        editor.apply()
    }
}