package com.georgevik.rickandmorty.di

import com.georgevik.rickandmorty.di.modules.AppModule
import com.georgevik.rickandmorty.di.modules.DatabaseModule
import com.georgevik.rickandmorty.di.modules.HttpModule
import com.georgevik.rickandmorty.di.modules.ViewModelModule
import com.georgevik.rickandmorty.views.character.CharacterActivity
import com.georgevik.rickandmorty.views.character.CharacterListFragment
import com.georgevik.rickandmorty.views.characterDetail.CharacterDetailActivity
import com.georgevik.rickandmorty.views.characterDetail.CharacterDetailFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    DatabaseModule::class,
    HttpModule::class,
    ViewModelModule::class
])
interface AppComponent {
    fun inject(source: CharacterActivity)
    fun inject(source: CharacterListFragment)
    fun inject(source: CharacterDetailActivity)
    fun inject(source: CharacterDetailFragment)
}