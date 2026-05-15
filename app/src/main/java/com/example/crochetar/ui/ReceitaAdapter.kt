package com.example.crochetar.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crochetar.R
import com.example.crochetar.model.Receita
import android.net.Uri
import android.widget.ImageButton

class ReceitaAdapter(
    private val lista: List<Receita>
) : RecyclerView.Adapter<ReceitaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val btnFavorito: ImageButton =
            view.findViewById(R.id.btnFavorito)

        val nome: TextView = view.findViewById(R.id.txtNome)
        val categoria: TextView = view.findViewById(R.id.txtCategoria)
        val imagem: ImageView = view.findViewById(R.id.imgReceita)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_receita, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val receita = lista[position]

        if (receita.favorita) {

            holder.btnFavorito.setImageResource(
                android.R.drawable.star_big_on
            )

        } else {

            holder.btnFavorito.setImageResource(
                android.R.drawable.star_big_off
            )
        }

        holder.btnFavorito.setOnClickListener {

            receita.favorita = !receita.favorita
            com.example.crochetar.data.StorageUtil.salvarReceitas(
                holder.itemView.context,
                lista
            )

            notifyItemChanged(position)
        }



        holder.nome.text = receita.nome
        holder.categoria.text = receita.categoria
        if (receita.imagemUri != null) {

            holder.imagem.setImageURI(Uri.parse(receita.imagemUri))

        } else if (receita.imagemRes != null) {

            receita.imagemRes?.let {

                holder.imagem.setImageResource(it)
            }
        }

        holder.itemView.setOnClickListener {

            val context = holder.itemView.context

            val intent = Intent(context, DetalhesActivity::class.java)

            intent.putExtra("id", receita.id)
            intent.putExtra("nome", receita.nome)
            intent.putExtra("categoria", receita.categoria)
            intent.putExtra("descricao", receita.descricao)
            intent.putExtra("imagem", receita.imagemRes)
            intent.putExtra("imagemUri", receita.imagemUri)

            context.startActivity(intent)
        }
    }
}