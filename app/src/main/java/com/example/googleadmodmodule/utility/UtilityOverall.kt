package com.example.googleadmodmodule.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

object UtilityOverall {

    /** is Internet Connected?
     * yes-> return true
     * no-> return false*/
    fun isInternetConnected(context: Context): Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        var output = false
        val network: Network? = connectivityManager?.activeNetwork


        connectivityManager.run {
            connectivityManager?.getNetworkCapabilities(network)?.run {
                output = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }

            }
        }

        return output
    }
}