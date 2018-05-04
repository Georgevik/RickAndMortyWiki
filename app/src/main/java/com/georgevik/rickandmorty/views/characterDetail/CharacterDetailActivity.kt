package com.georgevik.rickandmorty.views.characterDetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.georgevik.rickandmorty.R
import com.georgevik.rickandmorty.views.RootActivity
import com.georgevik.rickandmorty.views.character.CharacterViewModel
import javax.inject.Inject

class CharacterDetailActivity : RootActivity() {

    @Inject
    lateinit var viewModel : CharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = CharacterDetailFragment()
            supportFragmentManager.beginTransaction().add(R.id.flActivityDetailCharacter_frameFragment, fragment).commit()
        }
    }

    companion object {
        fun buildIntent(activity: Activity): Intent {
            val intent = Intent(activity, CharacterDetailActivity::class.java)
            return intent
        }
    }
}
