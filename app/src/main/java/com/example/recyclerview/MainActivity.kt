package com.example.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.adapters.ListaAdapter
import com.example.recyclerview.adapters.ListaAdapterListener
import com.example.recyclerview.model.Lista
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_list.*


class MainActivity : AppCompatActivity(), ListaAdapterListener {
    private lateinit var adapter: ListaAdapter
    private var positionSelected: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ListaAdapter(this)
        listTarefas.adapter = adapter
        listTarefas.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        btADD.setOnClickListener{
            val lista = Lista(
                ptTitulo.text.toString(),
                ptDescricao.text.toString()
            )
            if(ptTitulo.text.toString() != "" && ptDescricao.text.toString() != "") {
                var position: Int = 0

                if (this.positionSelected >= 0) {
                    adapter.edit(lista, this.positionSelected)
                    position = this.positionSelected
                } else {
                    position = adapter.add(lista)
                }

                (listTarefas.layoutManager as LinearLayoutManager).scrollToPosition(position)
                clear()
            }
        }

        btRemove.setOnClickListener{
            if(this.positionSelected >= 0){
                adapter.remove(this.positionSelected)
                (listTarefas.layoutManager as LinearLayoutManager).scrollToPosition(0)
                clear()
            }
        }


    }

    private fun clear(){
        ptTitulo.text = null
        ptDescricao.text = null
        this.positionSelected = -1
        btRemove.isClickable = false
    }

    override fun onListaSelected(lista: Lista, position: Int) {
        ptTitulo.setText(lista.titulo)
        ptDescricao.setText(lista.descricao)

        this.positionSelected = position
        btRemove.isClickable = true
    }

    override fun onLongListaPress(lista: Lista, position: Int){
        lista.titulo = "[FEITO]"+lista.titulo
        lista.descricao

        adapter.edit(lista, position)

        (listTarefas.layoutManager as LinearLayoutManager).scrollToPosition(position)
    }

}