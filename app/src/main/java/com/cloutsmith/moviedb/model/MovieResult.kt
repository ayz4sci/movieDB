package com.cloutsmith.moviedb.model

import com.google.gson.annotations.SerializedName

/**
 * Created by @ayz4sci on 28/01/2018.
 */

data class MovieResult(
        val page: Int,
        @SerializedName("total_results") val totalResults: Int,
        @SerializedName("total_pages") val totalPages: Int,
        @SerializedName("results") val movies: ArrayList<Movie>
)