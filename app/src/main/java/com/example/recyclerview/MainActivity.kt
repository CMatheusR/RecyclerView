package com.example.recyclerview

import android.content.Intent
import android.icu.text.Transliterator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.adapters.ItemAdapter
import com.example.recyclerview.adapters.ItemAdapterListener
import com.example.recyclerview.model.Item
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_list_add_show.*


class MainActivity : AppCompatActivity(), ItemAdapterListener {
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ItemAdapter(this, applicationContext)
        listTarefas.adapter = adapter
        listTarefas.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        btAdd.setOnClickListener{
            adapter.novoCard()
        }
    }

    override fun save(item: Item){
        adapter.save(item)
    }

    override fun show(item: Item, position: Int) {
        adapter.show(item, position)
    }

    override fun remove(item: Item, position: Int){
        adapter.remove(item, position)
    }

    override fun share(item: Item) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.compartilhar) +" "+ item.titulo)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, null))
    }

}