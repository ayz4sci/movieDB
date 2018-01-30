package com.cloutsmith.moviedb.network

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by @ayz4sci on 30/01/2018.
 */
class NetworkUtil {
    companion object {
        fun isConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }
}