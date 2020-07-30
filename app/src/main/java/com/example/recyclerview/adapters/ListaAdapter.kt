package com.example.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.model.Lista
import kotlinx.android.synthetic.main.item_list.view.*

class ListaAdapter(val listener: ListaAdapterListener) :
    RecyclerView.Adapter<ListaAdapter.ViewHolder>() {

    private val tarefa = Lista.example()

    fun add(lista: Lista): Int {
        val position = itemCount
        tarefa.add(position, lista)
        notifyItemInserted(position)
        return position
    }

    fun edit(lista: Lista, position: Int){
        tarefa[position] = lista
        notifyItemChanged(position)
    }

    fun remove(position: Int){
        tarefa.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeRemoved(position, itemCount)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder (
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        )

    override fun getItemCount() = tarefa.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lista = tarefa[position]
        holder.fillView(lista, position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun fillView(lista: Lista, position: Int) {
            itemView.tvTitulo.text = lista.titulo
            itemView.tvDescricao.text = lista.descricao

            itemView.setOnClickListener {
                listener.onListaSelected(lista, position)
            }
            itemView.setOnLongClickListener{
                listener.onLongListaPress(lista, position)
                true
            }
        }
    }

}