package com.example.recyclerview.adapters

import com.example.recyclerview.model.Item

interface ItemAdapterListener {
    fun save(item: Item)
    fun show(item: Item, position: Int)
    fun remove(item: Item, position: Int)
    fun share(item: Item)

}
