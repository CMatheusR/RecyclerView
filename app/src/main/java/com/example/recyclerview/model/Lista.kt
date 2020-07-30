package com.example.recyclerview.model

data class Lista(var titulo: String, var descricao: String) {
    companion object{
        fun example() = mutableListOf(
            Lista("Cozinha", "Limpar a cozinha"),
            Lista("Quarto", "Arrumar a cama"),
            Lista("Sala", "Limpar a sala e tirar as coisa blablabla blablabla blabla bla bla bla lba")
        )
    }
}