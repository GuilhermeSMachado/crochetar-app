package com.example.crochetar.ui

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.crochetar.R
import com.example.crochetar.data.MockData
import com.example.crochetar.model.Receita
import com.example.crochetar.data.StorageUtil

class NovaReceitaActivity : AppCompatActivity() {

    private var imagemSelecionada: Uri? = null

    private val selecionarImagem =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->

            if (uri != null) {

                contentResolver.takePersistableUriPermission(
                    uri,
                    android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                imagemSelecionada = uri

                val imgPreview = findViewById<ImageView>(R.id.imgPreview)

                imgPreview.setImageURI(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_nova_receita)

        val edtNome = findViewById<EditText>(R.id.edtNome)
        val edtCategoria = findViewById<EditText>(R.id.edtCategoria)
        val edtDescricao = findViewById<EditText>(R.id.edtDescricao)

        val btnSalvar = findViewById<Button>(R.id.btnSalvar)
        val btnSelecionarImagem =
            findViewById<Button>(R.id.btnSelecionarImagem)

        btnSelecionarImagem.setOnClickListener {

            selecionarImagem.launch(arrayOf("image/*"))
        }


        btnSalvar.setOnClickListener {

            val receita = Receita(
                (MockData.receitas.maxOfOrNull { it.id } ?: 0) + 1,
                edtNome.text.toString(),
                edtCategoria.text.toString(),
                edtDescricao.text.toString(),
                imagemUri = imagemSelecionada?.toString()
            )

            MockData.receitas.add(receita)

            StorageUtil.salvarReceitas(
                this,
                MockData.receitas
            )

            StorageUtil.salvarReceitas(
                this,
                MockData.receitas
            )

            Toast.makeText(
                this,
                "Receita adicionada!",
                Toast.LENGTH_SHORT
            ).show()

            finish()

        }
    }
}