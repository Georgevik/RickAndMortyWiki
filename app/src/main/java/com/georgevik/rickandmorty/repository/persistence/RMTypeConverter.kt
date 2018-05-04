package com.georgevik.rickandmorty.repository.persistence

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


object RMTypeConverter {

    // Date

    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): Date? = if (null == value) null else Date(value)

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): Long? = date?.time

    // URL

    @TypeConverter
    @JvmStatic
    fun fromURL(value: String?): URL? = if (null == value) null else URL(value)

    @TypeConverter
    @JvmStatic
    fun stringToURL(date: URL?): String? = date?.toString()

    // ArrayList<String>

    @TypeConverter
    @JvmStatic
    fun fromStringToArrayListString(value: String?): ArrayList<String> = if (null == value) ArrayList() else Gson().fromJson(value, object : TypeToken<ArrayList<String>>() {}.type)

    @TypeConverter
    @JvmStatic
    fun arrayListStringToJson(data: ArrayList<String>?): String? = if (data == null) null else Gson().toJson(data)
}