package com.example.recyclerview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.adapters.ItemAdapter
import com.example.recyclerview.adapters.ItemAdapterListener
import com.example.recyclerview.model.Item
import kotlinx.android.synthetic.main.activity_main.*


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

    override fun show(item: Item) {
        adapter.show(item)
        //esse programa é burro
    }

    override fun remove(item: Item){
        adapter.remove(item)
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