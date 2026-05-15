package com.example.crochetar.ui

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.crochetar.R
import android.widget.Button
import com.example.crochetar.data.MockData
import com.example.crochetar.data.StorageUtil
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class DetalhesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detalhes)

        val nome = intent.getStringExtra("nome")
        val categoria = intent.getStringExtra("categoria")
        val descricao = intent.getStringExtra("descricao")

        val imagem = intent.getIntExtra("imagem", 0)
        val imagemUri = intent.getStringExtra("imagemUri")

        val img = findViewById<ImageView>(R.id.imgDetalhe)
        val txtNome = findViewById<TextView>(R.id.txtNomeDetalhe)
        val txtCategoria = findViewById<TextView>(R.id.txtCategoriaDetalhe)
        val txtDescricao = findViewById<TextView>(R.id.txtDescricaoDetalhe)

        txtNome.text = nome
        txtCategoria.text = categoria
        txtDescricao.text = descricao

        val id = intent.getIntExtra("id", -1)

        val btnEditar =
            findViewById<Button>(R.id.btnEditar)

        btnEditar.setOnClickListener {

            val intent =
                Intent(this, EditarReceitaActivity::class.java)

            intent.putExtra("id", id)

            startActivity(intent)
        }

        val btnExcluir = findViewById<Button>(R.id.btnExcluir)

        btnExcluir.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Excluir receita")
                .setMessage("Deseja realmente excluir esta receita?")
                .setPositiveButton("Sim") { _, _ ->

                    MockData.receitas.removeIf { it.id == id }

                    StorageUtil.salvarReceitas(
                        this,
                        MockData.receitas
                    )

                    finish()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        //Correção de Crash no app
        if (imagemUri != null) {

            img.setImageURI(Uri.parse(imagemUri))

        } else if (imagem != 0) {

            img.setImageResource(imagem)
        }
    }
}