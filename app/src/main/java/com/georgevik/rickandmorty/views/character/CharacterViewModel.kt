package com.georgevik.rickandmorty.views.character

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import android.util.Log
import com.georgevik.rickandmorty.pojo.Character
import com.georgevik.rickandmorty.repository.CharacterRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CharacterViewModel
@Inject constructor(private var repo: CharacterRepository) : ViewModel() {

    private var mFirstLoad: Boolean = false
    private var mCharacterSelected: MutableLiveData<Character> = MutableLiveData()
    private var mAdapterView: CharacterAdapter? = null
    private var mObserverItemSelected = Observer<Character> {
        mCharacterSelected.postValue(it)
    }

    private var mObserverUpdateList = Observer<PagedList<Character>> {
        if (it != null) {
            Log.d("CharacterViewModel", "Paged list submited +" + it.size)
            mAdapterView?.submitList(it)
            if (it.size == 0) {
                this.firstLoad()
            }
        }
    }

    fun getAdapterView(): CharacterAdapter {
        if (mAdapterView == null) {
            mAdapterView = this.setupAdapterView()
        }

        return mAdapterView!!
    }

    fun getCharacterSelected(): LiveData<Character> = mCharacterSelected

    fun loadMoreCharacters() = repo.loadMoreCharacters()

    override fun onCleared() {
        super.onCleared()
        mAdapterView?.getItemSelected()?.removeObserver(mObserverItemSelected)
        repo.getPagedCharacters().removeObserver(mObserverUpdateList)
    }

    // ====== Private Methods ======
    private fun setupAdapterView(): CharacterAdapter {
        // Create adapter view with an listener when end is reached
        val mAdapterView = CharacterAdapter().apply {
            onEndReached = object : CharacterAdapter.OnEndReached {
                override fun onEndReached() {
                    loadMoreCharacters()
                }
            }
        }

        // Add item click listener
        mAdapterView.getItemSelected().observeForever(mObserverItemSelected)

        // Update adapterView when add more DataSource
        repo.getPagedCharacters().observeForever(mObserverUpdateList)

        return mAdapterView
    }

    private fun firstLoad() {
        if (!mFirstLoad) {
            mFirstLoad = true
            this.loadMoreCharacters()
        }
    }

}