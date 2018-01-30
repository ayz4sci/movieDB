package com.cloutsmith.moviedb.network

import com.cloutsmith.moviedb.BASE_URL
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by @ayz4sci on 28/01/2018.
 */

class NetworkService {
    val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    fun getMovies(page: Int, year: String, callback: Callback) {
        val client = OkHttpClient()
        val request: Request = Request.Builder()
                .url(String.format(BASE_URL, page, year, sdf.format(Calendar.getInstance().time)))
                .get()
                .build()

        val call = client.newCall(request)
        call.enqueue(callback)
    }
}