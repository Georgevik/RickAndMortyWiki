package com.georgevik.rickandmorty.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.georgevik.rickandmorty.repository.persistence.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        var room = Room.databaseBuilder(context,
                AppDatabase::class.java,
                "database.db")
                .fallbackToDestructiveMigration()


        return room.build()
    }

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.characterDao()
}