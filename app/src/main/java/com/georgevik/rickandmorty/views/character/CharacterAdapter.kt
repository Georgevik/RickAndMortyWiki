package com.georgevik.rickandmorty.views.character

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.georgevik.rickandmorty.R
import com.georgevik.rickandmorty.pojo.Character
import com.georgevik.rickandmorty.views.OnItemClick

class CharacterAdapter() : PagedListAdapter<Character, CharacterViewHolder>(DIFF_CALLBACK) {

    private var mItemSelected = MutableLiveData<Character>()
    var onEndReached: OnEndReached? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        if (position == this.itemCount - 1) {
            onEndReached?.onEndReached()
        }

        if (character != null) {
            holder.bindTo(character, object : OnItemClick<Character> {
                override fun onItemSelected(item: Character) {
                    mItemSelected.value = item
                }
            })
        } else {
            holder.clear()
        }
    }

    fun getItemSelected(): LiveData<Character> = mItemSelected

    companion object {
        private var DIFF_CALLBACK = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

    interface OnEndReached {
        fun onEndReached()
    }
}


