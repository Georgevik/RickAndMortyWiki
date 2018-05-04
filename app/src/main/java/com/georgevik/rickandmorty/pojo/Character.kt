package com.georgevik.rickandmorty.pojo

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.net.URL

@Entity
class Character {
    @PrimaryKey
    @SerializedName("id")
    var id: Int = -1

    @SerializedName("name")
    var name: String = "Unknown"

    @SerializedName("status")
    var status: String = "Unknown"

    @SerializedName("species")
    var species: String = "Unknown"

    @SerializedName("type")
    var type: String = "Unknown"

    @SerializedName("gender")
    var gender: String = "Unknown"

    @SerializedName("origin")
    @Embedded(prefix = "origin")
    var origin: NameLink? = null

    @SerializedName("location")
    @Embedded(prefix = "location")
    var location: NameLink? = null

    @SerializedName("image")
    var image: String? = null

    @SerializedName("episode")
    var episode: ArrayList<String> = ArrayList()

    @SerializedName("url")
    var url: String? = null

    @SerializedName("created")
    var created: String = "Unknown"

    override fun toString(): String {
        return name
    }

    fun getUrlImage(): URL? {
        return try {
            URL(image)
        } catch (e: Exception) {
            null
        }
    }
}