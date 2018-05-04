package com.georgevik.rickandmorty.repository.persistence

import android.arch.persistence.room.*

@Dao
abstract class BaseDao<in T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg data: T): Array<Long>

    @Update
    abstract fun update(data: T)

    @Delete
    abstract fun delete(data: T)
}