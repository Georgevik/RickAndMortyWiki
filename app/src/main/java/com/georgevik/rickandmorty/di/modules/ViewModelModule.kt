package com.georgevik.rickandmorty.di.modules

import com.georgevik.rickandmorty.repository.CharacterRepository
import com.georgevik.rickandmorty.views.character.CharacterViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Singleton
    @Provides
    fun provideCharacterVm(repository: CharacterRepository): CharacterViewModel {
        return CharacterViewModel(repository)
    }
}