package com.georgevik.rickandmorty.di.modules

import android.content.Context
import com.georgevik.rickandmorty.AppAplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: AppAplication){

    @Singleton
    @Provides
    fun provideContext() : Context = app.applicationContext

    @Singleton
    @Provides
    fun provideApplication() : AppAplication = app
}