package com.cloutsmith.moviedb.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by @ayz4sci on 28/01/2018.
 */

data class Movie(
        val id: Long,
        @SerializedName("title") val title: String,
        @SerializedName("backdrop_path") val imageUrl: String,
        @SerializedName("overview") val description: String,
        @SerializedName("release_date") val date: String
) : Serializable