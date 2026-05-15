package com.example.crochetar.model

data class Receita(
    val id: Int,
    var nome: String,
    var categoria: String,
    var descricao: String,
    var imagemRes: Int? = null,
    var imagemUri: String? = null,
    var favorita: Boolean = false
)