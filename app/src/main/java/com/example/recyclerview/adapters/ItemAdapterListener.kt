package com.example.recyclerview.adapters

import com.example.recyclerview.model.Item

interface ItemAdapterListener {
    fun save(item: Item)
    fun show(item: Item)
    fun remove(item: Item)
    fun share(item: Item)

}
