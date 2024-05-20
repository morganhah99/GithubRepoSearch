package com.example.android.codelabs.paging.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast


fun isDeviceOnline(context: Context): Boolean {

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }

}

fun showConnectionStatus(context: Context) {
    if (!isDeviceOnline(context)) {
        Toast.makeText(context, "You are offline, you can still search for cached queries", Toast.LENGTH_LONG).show()
    } else {
        Toast.makeText(context, "You are online, you can search for any repository", Toast.LENGTH_LONG).show()
    }
}