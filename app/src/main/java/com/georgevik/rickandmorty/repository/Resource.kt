package com.georgevik.rickandmorty.repository

import com.georgevik.rickandmorty.repository.webservices.Status


class Resource<T>(
        val status: Status = Status.ERROR,
        val message: String? = null,
        val data: T? = null
) {

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, null, data)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, msg, data)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, null, data)
        }
    }
}