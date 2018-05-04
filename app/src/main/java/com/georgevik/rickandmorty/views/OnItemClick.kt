package com.georgevik.rickandmorty.views

interface OnItemClick<TypeItem> {
    fun onItemSelected(item: TypeItem)
}