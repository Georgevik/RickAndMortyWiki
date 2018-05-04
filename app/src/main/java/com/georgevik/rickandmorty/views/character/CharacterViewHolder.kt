package com.georgevik.rickandmorty.views.character

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.georgevik.rickandmorty.R
import com.georgevik.rickandmorty.pojo.Character
import com.georgevik.rickandmorty.views.OnItemClick
import com.squareup.picasso.Picasso

class CharacterViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val root: View = view.findViewById(R.id.vRowCharacter)
    private val name: TextView = view.findViewById(R.id.tvRowCharacter_name)
    private val specie: TextView = view.findViewById(R.id.tvRowCharacter_specie)
    private val image: ImageView = view.findViewById(R.id.ivRowCharacter)

    fun bindTo(character: Character, onListItemClick: OnItemClick<Character>) {
        name.text = character.name
        specie.text = character.species

        val url = character.getUrlImage()
        if (url != null)
            Picasso.get().load(url.toString()).into(image)
        root.tag = character
        root.setOnClickListener { onListItemClick.onItemSelected(it.tag as Character) }
    }

    fun clear() {
        name.text = ""
    }
}