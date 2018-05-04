package com.georgevik.rickandmorty.views.character

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.georgevik.rickandmorty.AppAplication
import com.georgevik.rickandmorty.R
import com.georgevik.rickandmorty.views.RootActivity
import com.georgevik.rickandmorty.views.characterDetail.CharacterDetailActivity
import com.georgevik.rickandmorty.views.characterDetail.CharacterDetailFragment
import kotlinx.android.synthetic.main.activity_character.*
import javax.inject.Inject


class CharacterActivity : RootActivity() {
    @Inject
    lateinit var viewModel: CharacterViewModel
    private var mTwoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        (application as AppAplication).appComponent.inject(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.setFragments()

        this.setupOnItemSelected();
    }

    private fun setFragments() {
        if (flActivityCharacter_frameDetail != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            (supportFragmentManager.findFragmentById(R.id.fActivityCharacter_listFragment) as CharacterListFragment)
        }
    }

    private fun setupOnItemSelected() {
        this.viewModel.getCharacterSelected().observe(this, Observer { item ->
            if (item == null) {
                return@Observer
            }

            if (mTwoPane) {
                // In two-pane mode, show the detail view in this activity by
                // adding or replacing the detail fragment using a
                // fragment transaction.
                val fragmentAttached = supportFragmentManager.fragments.fold(false, { attached, fragment ->
                    attached || fragment is CharacterDetailFragment
                })

                if (!fragmentAttached) {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.flActivityCharacter_frameDetail, CharacterDetailFragment())
                            .commit()
                }

            } else {
                // In single-pane mode, simply start the detail activity
                // for the selected item ID.
                startActivity(CharacterDetailActivity.buildIntent(this))
            }
        })
    }
}
