package com.georgevik.rickandmorty.pojo

import com.google.gson.annotations.SerializedName

class Episode {
    @SerializedName("id")
    val id: Int = -1
    @SerializedName("name")
    val name: String = "Unknown"
    @SerializedName("air_date")
    val air_date: String = "Unknown"
    @SerializedName("episode")
    val episode: String = "Unknown"
    @SerializedName("characters")
    val characters: ArrayList<String> = ArrayList()
    @SerializedName("url")
    val url: String? = null
    @SerializedName("created")
    val created: String = "Unknown"
}