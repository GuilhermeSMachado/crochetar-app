package com.example.crochetar.data

import com.example.crochetar.model.Receita

object MockData {

    val receitas = mutableListOf(

        Receita(
            1,
            "Urso Amigurumi",
            "Amigurumi",
            "Passo a passo...",
            android.R.drawable.ic_menu_gallery
        ),

        Receita(
            2,
            "Tapete Redondo",
            "Decoração",
            "Tapete simples...",
            android.R.drawable.ic_menu_gallery
        )
    )
}