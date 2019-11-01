package com.vikaspandey.demo1.utils

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

@Suppress("DEPRECATION")
class NetworkUtils @Inject constructor(private val context: Context) {
    fun isInternetAvailable(): Boolean {
        val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return (connectivityManager.activeNetworkInfo == null ||
                !connectivityManager.activeNetworkInfo.isConnected).not()
    }
}