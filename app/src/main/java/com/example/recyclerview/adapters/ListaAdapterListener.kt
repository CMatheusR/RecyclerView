package com.example.recyclerview.adapters

import com.example.recyclerview.model.Lista

interface ListaAdapterListener {
    fun onListaSelected(lista: Lista, position: Int)
    fun onLongListaPress(lista: Lista, position: Int)
}
