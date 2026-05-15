package com.example.crochetar.data

import android.content.Context
import com.example.crochetar.model.Receita
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object StorageUtil {

    private const val PREF_NAME = "crochetar_pref"
    private const val KEY_RECEITAS = "receitas"

    fun salvarReceitas(
        context: Context,
        lista: List<Receita>
    ) {

        val prefs =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val editor = prefs.edit()

        val json = Gson().toJson(lista)

        editor.putString(KEY_RECEITAS, json)

        editor.apply()
    }

    fun carregarReceitas(context: Context): MutableList<Receita> {

        val prefs =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val json = prefs.getString(KEY_RECEITAS, null)

        return if (json != null) {

            val type =
                object : TypeToken<MutableList<Receita>>() {}.type

            Gson().fromJson(json, type)

        } else {

            mutableListOf()
        }
    }
}