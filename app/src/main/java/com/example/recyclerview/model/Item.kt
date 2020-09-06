package com.example.recyclerview.model

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("titulo") var titulo: String,
    @SerializedName("descricao") var descricao: String,
    @SerializedName("finalizada") var finalizado: Boolean,
    @SerializedName("operacao") var operacao: Int,
    @SerializedName("longitude") var longitude: Double,
    @SerializedName("latitude") var latidude: Double
    //1 - Cadastrar | 2 - Show
) {
    var id: Long? = null
}