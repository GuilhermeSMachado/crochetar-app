package com.example.crochetar.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crochetar.R
import com.example.crochetar.data.MockData
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.crochetar.data.StorageUtil
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.AdapterView
import android.view.View
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var adapter: ReceitaAdapter
    lateinit var txtVazio: TextView
    private var listaFiltrada = mutableListOf<com.example.crochetar.model.Receita>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        txtVazio = findViewById<TextView>(R.id.txtVazio)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val fabNovaReceita =
            findViewById<FloatingActionButton>(
                R.id.fabNovaReceita
            )
        val edtPesquisa = findViewById<EditText>(R.id.edtPesquisa)

        val receitasSalvas = StorageUtil.carregarReceitas(this)

        if (receitasSalvas.isNotEmpty()) {

            MockData.receitas.clear()
            MockData.receitas.addAll(receitasSalvas)
        }
        val categorias = listOf(

            "Todas",
            "Amigurumi",
            "Roupas",
            "Decoração",
            "Favoritas"
        )
        val spinnerCategoria =
            findViewById<Spinner>(R.id.spinnerCategoria)

        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categorias
        )


        spinnerAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinnerCategoria.adapter = spinnerAdapter

        spinnerCategoria.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    val texto =
                        edtPesquisa.text.toString()

                    val categoria =
                        categorias[position]

                    filtrarReceitas(texto, categoria)
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>?
                ) {
                }
            }


        listaFiltrada.addAll(MockData.receitas)

        adapter = ReceitaAdapter(listaFiltrada)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fabNovaReceita.setOnClickListener {

            val intent = Intent(this, NovaReceitaActivity::class.java)

            startActivity(intent)
        }

        edtPesquisa.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

                val texto = s.toString()

                val categoriaSelecionada =
                    spinnerCategoria.selectedItem.toString()

                filtrarReceitas(texto, categoriaSelecionada)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    override fun onResume() {
        super.onResume()

        listaFiltrada.clear()
        listaFiltrada.addAll(MockData.receitas)

        adapter.notifyDataSetChanged()
    }
    private fun filtrarReceitas(
        texto: String,
        categoria: String
    ) {

        listaFiltrada.clear()


        val resultado = MockData.receitas.filter {

            val correspondeTexto =
                it.nome.lowercase().contains(texto.lowercase())

            val correspondeCategoria = when (categoria) {

                "Todas" -> true

                "Favoritas" -> it.favorita

                else -> it.categoria == categoria
            }

            correspondeTexto && correspondeCategoria
        }

        listaFiltrada.addAll(resultado)

        txtVazio.visibility =
            if (listaFiltrada.isEmpty())
                View.VISIBLE
            else
                View.GONE

        adapter.notifyDataSetChanged()
    }

}