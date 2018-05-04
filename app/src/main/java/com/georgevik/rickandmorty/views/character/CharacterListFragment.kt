package com.georgevik.rickandmorty.views.character

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.georgevik.rickandmorty.AppAplication
import com.georgevik.rickandmorty.R
import javax.inject.Inject

class CharacterListFragment : Fragment() {
    @Inject
    lateinit var mViewModel: CharacterViewModel
    private lateinit var mRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity?.application as AppAplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_list_character, container, false)

        mRecyclerView = root.findViewById(R.id.rvFragmentList)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.setupRecycler()
    }

    private fun setupRecycler() {
        mRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = this@CharacterListFragment.mViewModel.getAdapterView()
        }
    }
}