package com.example.crochetar.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.crochetar.R
import com.example.crochetar.data.MockData
import com.example.crochetar.data.StorageUtil
import android.widget.Toast

class EditarReceitaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_editar_receita)

        val id = intent.getIntExtra("id", -1)

        val receita =
            MockData.receitas.find { it.id == id }

        val edtNome =
            findViewById<EditText>(R.id.edtNome)

        val edtCategoria =
            findViewById<EditText>(R.id.edtCategoria)

        val edtDescricao =
            findViewById<EditText>(R.id.edtDescricao)

        val btnSalvar =
            findViewById<Button>(R.id.btnSalvarEdicao)

        if (receita != null) {

            edtNome.setText(receita.nome)
            edtCategoria.setText(receita.categoria)
            edtDescricao.setText(receita.descricao)
        }

        btnSalvar.setOnClickListener {

            receita?.nome =
                edtNome.text.toString()

            receita?.categoria =
                edtCategoria.text.toString()

            receita?.descricao =
                edtDescricao.text.toString()

            StorageUtil.salvarReceitas(
                this,
                MockData.receitas
            )

            finish()

            Toast.makeText(
                this,
                "Receita atualizada!",
                Toast.LENGTH_SHORT
            ).show()

        }
    }
}