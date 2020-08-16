package com.example.recyclerview.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Itens")
data class Item(
    @ColumnInfo(name = "titulo") var titulo: String?,
    @ColumnInfo(name = "descricao") var descricao: String?,
    @ColumnInfo(name = "finalizado") var finalizado: Boolean?,
    @ColumnInfo(name = "operacao") var operacao: Int
    //1 - Cadastrar | 2 - Show
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}