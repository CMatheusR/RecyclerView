package com.example.recyclerview.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.recyclerview.R
import com.example.recyclerview.database.AppDatabase
import com.example.recyclerview.database.dao.ItemDao
import com.example.recyclerview.model.Item
import kotlinx.android.synthetic.main.activity_item_list_show.view.*
import kotlinx.android.synthetic.main.item_list_add_show.view.*

class ItemAdapter(val listener: ItemAdapterListener, context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val CADASTRO: Int = 0
    val SHOW: Int = 1

    private val dao: ItemDao
    private val tarefas: MutableList<Item>

    init {
        val db = Room.databaseBuilder(context, AppDatabase::class.java, "Item-db")
            .allowMainThreadQueries()
            .build()
        dao = db.ItemDao()
        tarefas = dao.getAll().toMutableList()
    }

    override fun getItemViewType(position: Int): Int {
        val item = tarefas[position]
        return when (item.operacao) {
            1 -> {
                this.CADASTRO
            }
            2 -> {
                this.SHOW
            }
            else -> -1
        }
    }

    //vc está clicando duas vezes, ou está adicionando dois card? cliquei duas vezes sem quererh

    fun save(item: Item): Int {

        Log.d("testando", "item = $item")
        return if (item.id == 0L) {
            item.operacao = 2
            item.id = dao.insert(item)

            val position = tarefas.indexOf(item)
            notifyItemChanged(position)
            position
        } else {
            item.operacao = 2
            dao.update(item)
            val position = tarefas.indexOf(item)
            notifyItemChanged(position)
            position
        }
    }

    fun remove(item: Item, position: Int) {
        dao.delete(item)
        tarefas.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeRemoved(position, itemCount)
    }

    fun novoCard(): Int {
        val item = Item("", "", false, 1)
        val position = 0
        tarefas.add(position, item)
        notifyItemInserted(position)
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View?
        var viewHolder: RecyclerView.ViewHolder

        return if (viewType == CADASTRO) {
            view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_list_add_show, parent, false)
            viewHolder = ViewHolderCadastroEditar(view)
            viewHolder
        } else {
            view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.activity_item_list_show, parent, false)
            viewHolder = ViewHolderShow(view)
            viewHolder
        }

    }

    override fun getItemCount() = tarefas.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = tarefas[position]
        when (holder.itemViewType) {
            0 -> {
                var viewHolderCadastroEditar: ViewHolderCadastroEditar =
                    holder as ViewHolderCadastroEditar
                viewHolderCadastroEditar.fillView(item, position)
            }
            1 -> {
                var viewHolderShow: ViewHolderShow = holder as ViewHolderShow
                viewHolderShow.fillView(item, position)
            }
        }
    }

    inner class ViewHolderCadastroEditar(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun fillView(item: Item, position: Int) {

            itemView.tfTitulo.text = null
            itemView.tfDescricao.text = null

            if (!item.titulo.equals(null) && !item.descricao.equals(null)){
                itemView.tfTitulo.setText(item.titulo)
                itemView.tfDescricao.setText(item.descricao)
            }
            itemView.btSalvar.setOnClickListener {
                item.titulo = itemView.tfTitulo.text.toString()
                item.descricao = itemView.tfDescricao.text.toString()
                if(item.finalizado == null){
                    item.finalizado = false
                }

                if (!item.titulo.equals("") && !item.descricao.equals("")) {
                    listener.save(item)
                }

            }
            itemView.btCancel.setOnClickListener {
                listener.remove(item, position)
            }
        }
    }

    inner class ViewHolderShow(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("ResourceAsColor")
        fun fillView(item: Item, position: Int) {
            itemView.lbTitulo.text = item.titulo
            if(item.finalizado?.equals(true)!!) {
                itemView.btShare.visibility = View.VISIBLE
                itemView.lbTitulo.setTextColor("#44FF00".toColorInt())
            }else{
                itemView.lbTitulo.setTextColor("#FF000000".toColorInt())
                itemView.btShare.visibility = View.GONE
            }
            itemView.btShare.setOnClickListener{
                listener.share(item)
            }
            itemView.cardCompacto.setOnClickListener {
                listener.show(item, position)
            }
            itemView.cardCompacto.setOnLongClickListener {
                if (item.finalizado == false) {
                    item.finalizado = true
                    listener.save(item)
                } else if (item.finalizado == true) {
                    item.finalizado = false
                    listener.save(item)
                }
                true
            }
        }
    }

    fun show(item: Item, position: Int){
        item.operacao = 1
        notifyItemChanged(position)
    }

}