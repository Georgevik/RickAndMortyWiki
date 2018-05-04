package com.georgevik.rickandmorty.views.characterDetail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.georgevik.rickandmorty.AppAplication
import com.georgevik.rickandmorty.R
import com.georgevik.rickandmorty.views.character.CharacterViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail_character.*
import javax.inject.Inject

class CharacterDetailFragment : Fragment() {

    @Inject
    lateinit var viewModel : CharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity?.application as AppAplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_detail_character, container, false)

        viewModel.getCharacterSelected().observe(this, Observer {
            if (it == null) {
                return@Observer
            }

            tvFragDetailCharacter_name.text = it.name
            Picasso.get().load(it.image).into(ivFragDetailCharacter)
        })

        return rootView
    }
}