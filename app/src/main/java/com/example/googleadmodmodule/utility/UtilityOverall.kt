package com.example.googleadmodmodule.utility

import android.content.Context
import android.net.ConnectivityManager

object UtilityOverall {
    fun isInternetConnected(context: Context): Boolean{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm?.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}