package com.georgevik.rickandmorty.repository.webservices

import com.georgevik.rickandmorty.pojo.Character
import com.georgevik.rickandmorty.repository.webservices.responses.ResponseList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Webservice {
    @GET("character")
    fun getAllChars(@Query("page") page: Int = 1): Call<ResponseList<Character>>
}