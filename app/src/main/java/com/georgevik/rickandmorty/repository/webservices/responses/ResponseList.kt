package com.georgevik.rickandmorty.repository.webservices.responses

import com.georgevik.rickandmorty.pojo.InfoWebservice
import com.google.gson.annotations.SerializedName

class ResponseList<T> {
    @SerializedName("info")
    val info: InfoWebservice = InfoWebservice()
    @SerializedName("results")
    val results: ArrayList<T> = ArrayList()
}