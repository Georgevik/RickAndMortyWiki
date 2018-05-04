package com.georgevik.rickandmorty

import android.app.Application
import com.georgevik.rickandmorty.di.AppComponent
import com.georgevik.rickandmorty.di.DaggerAppComponent
import com.georgevik.rickandmorty.di.modules.AppModule

class AppAplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}