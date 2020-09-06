package com.example.recyclerview.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.api.ItemService
import com.example.recyclerview.model.Item
import kotlinx.android.synthetic.main.activity_item_list_show.view.*
import kotlinx.android.synthetic.main.item_list_add_show.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemAdapter(val listener: ItemAdapterListener, context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val CADASTRO: Int = 0
    private val SHOW: Int = 1

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.254.6:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val service = retrofit.create(ItemService::class.java)
    private var tarefas = mutableListOf<Item>()


    init {
        Log.d("teste", "hello")

        service.getAll().enqueue(object : Callback<List<Item>> {
            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                Log.e("teste", t.message, t)

            }

            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                tarefas = response.body()!!.toMutableList()
                notifyDataSetChanged()
                Log.d("teste", "tarefas = " + tarefas)
            }
        })
    }



    //vc está clicando duas vezes, ou está adicionando dois card? cliquei duas vezes sem quererh

    fun save(item: Item){


        if (item.id == null) {

            item.operacao = 2

            service.insert(item).enqueue(object : Callback<Item>{
                override fun onFailure(call: Call<Item>, t: Throwable) {
                    Log.e("teste", t.message, t)
                }
                override fun onResponse(call: Call<Item>, response: Response<Item>) {
                    item.id = response.body()!!.id
                    notifyItemChanged(0)
                }
            })
        } else {
            item.operacao = 2

            service.update(item.id!!, item).enqueue(object : Callback<Item> {
                override fun onFailure(call: Call<Item>, t: Throwable) { /* ... */
                }

                override fun onResponse(call: Call<Item>, response: Response<Item>) {
                    val position = tarefas.indexOf(item)
                    notifyItemChanged(position)
                }
            })

        }
    }

    fun remove(item: Item) {

        service.delete(item.id!!).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) { /* ... */ }
            override fun onResponse(call: Call<Void>, response: Response<Void>) {}
        })

        var position = tarefas.indexOf(item)
        tarefas.removeAt(position)
        notifyItemRemoved(position)

    }

    fun novoCard(): Int {
        val item = Item("", "", false, 1, 0.0, 0.0)
        val position = 0
        tarefas.add(position, item)
        notifyItemInserted(position)
        return position
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

        Log.d("teste", "itemView = " + holder.itemViewType)

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
                itemView.btCancel.visibility = View.VISIBLE
            }
            else{
                itemView.btCancel.visibility = View.GONE
            }

            itemView.btSalvar.setOnClickListener {
                item.titulo = itemView.tfTitulo.text.toString()
                item.descricao = itemView.tfDescricao.text.toString()

                if (item.titulo != "" && item.descricao != "") {
                    listener.save(item)
                }

            }
            itemView.btCancel.setOnClickListener {
                listener.remove(item)
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
                listener.show(item)
            }
            itemView.cardCompacto.setOnLongClickListener {
                if (!item.finalizado) {
                    item.finalizado = true
                    listener.save(item)
                } else if (item.finalizado) {
                    item.finalizado = false
                    listener.save(item)
                }
                true
            }
        }
    }

    fun show(item: Item){
        item.operacao = 1
        val position = tarefas.indexOf(item)
        notifyItemChanged(position)
    }

}