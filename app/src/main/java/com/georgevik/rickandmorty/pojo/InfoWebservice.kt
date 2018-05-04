package com.georgevik.rickandmorty.pojo

import com.google.gson.annotations.SerializedName

class InfoWebservice {
    @SerializedName("count")
    val count: Int = 0
    @SerializedName("pages")
    val pages: Int = 0
    @SerializedName("next")
    val next: String? = null
    @SerializedName("prev")
    val prev: String? = null
}