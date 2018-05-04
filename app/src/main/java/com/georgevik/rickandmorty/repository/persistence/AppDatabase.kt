package com.georgevik.rickandmorty.repository.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.georgevik.rickandmorty.pojo.Character

@Database(entities = [Character::class], version = 1)
@TypeConverters(RMTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}