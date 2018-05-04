package com.georgevik.rickandmorty.pojo

import com.google.gson.annotations.SerializedName

class NameLink {
    @SerializedName("name")
    var name: String = "Unknown"

    @SerializedName("url")
    var url: String? = null
}