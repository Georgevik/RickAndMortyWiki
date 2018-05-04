package com.georgevik.rickandmorty.pojo

import com.google.gson.annotations.SerializedName

class Location {
    @SerializedName("id")
    val id: Int = -1
    @SerializedName("name")
    val name: String = "Unknown"
    @SerializedName("type")
    val type: String = "Unknown"
    @SerializedName("dimension")
    val dimension: String = "Unknown"
    @SerializedName("residents")
    val residents: ArrayList<String> = ArrayList()
    @SerializedName("url")
    val url: String? = null
    @SerializedName("created")
    val created: String = "Unknown"
}