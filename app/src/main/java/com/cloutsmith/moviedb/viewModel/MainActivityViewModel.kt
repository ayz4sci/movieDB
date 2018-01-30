package com.cloutsmith.moviedb.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.cloutsmith.moviedb.model.MovieResult
import com.cloutsmith.moviedb.network.NetworkService
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


/**
 * Created by @ayz4sci on 30/01/2018.
 */

class MainActivityViewModel : ViewModel() {
    private var liveData: MutableLiveData<MovieResult>? = null
    private val errors: MutableLiveData<String> = MutableLiveData()

    fun getMovies(): MutableLiveData<MovieResult> {
        if (liveData == null) {
            liveData = MutableLiveData()
            loadMovies()
        }
        return liveData!!
    }

    fun loadMovies(page: Int = 1, year: String = "2018") {
        NetworkService().getMovies(page, year, object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                errors.postValue(e!!.message)
            }

            override fun onResponse(call: Call?, response: Response?) {
                val movieResult = Gson().fromJson(response!!.body()!!.charStream(), MovieResult::class.java)
                liveData!!.postValue(movieResult)
            }
        })
    }


    fun getErrors(): MutableLiveData<String> = errors
}