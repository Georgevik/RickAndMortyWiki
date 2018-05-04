package com.georgevik.rickandmorty.repository

import android.arch.paging.DataSource
import com.georgevik.rickandmorty.pojo.Character
import com.georgevik.rickandmorty.repository.persistence.CharacterDao
import com.georgevik.rickandmorty.repository.webservices.NetworkResourcePaged
import com.georgevik.rickandmorty.repository.webservices.Webservice
import com.georgevik.rickandmorty.repository.webservices.responses.ResponseList
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(var webservice: Webservice,
                                              var characterDao: CharacterDao) {

    private var mLastCall: ResponseList<Character>? = null
    private val networkResourcePaged = object : NetworkResourcePaged<Character, ResponseList<Character>>() {
        override fun saveCallResult(item: ResponseList<Character>) {
            mLastCall = item
            characterDao.insertList(item.results)
        }

        override fun loadFromDb(): DataSource.Factory<Int, Character> {
            return characterDao.getAllPaged()
        }

        override fun createCall(): Call<ResponseList<Character>> {
            var page = characterDao.count() / 20 + 1
            return webservice.getAllChars(page)
        }

    }

    fun getPagedCharacters() = networkResourcePaged.getAsLiveData()

    fun loadMoreCharacters() = networkResourcePaged.loadNextPage()
}