package com.georgevik.rickandmorty.repository.persistence

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.georgevik.rickandmorty.pojo.Character


@Dao
abstract class CharacterDao : BaseDao<Character>() {
    @Query("SELECT * FROM Character")
    abstract fun getAll(): LiveData<List<Character>>

    @Query("SELECT * FROM Character")
    abstract fun getAllPaged(): DataSource.Factory<Int, Character>

    @Query("SELECT COUNT(*) FROM Character")
    abstract fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertList(data: List<Character>): Array<Long>
}