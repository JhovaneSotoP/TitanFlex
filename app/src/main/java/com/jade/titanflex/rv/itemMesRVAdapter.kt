package com.jade.titanflex.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jade.titanflex.OnItemClickListener
import com.jade.titanflex.R
import com.jade.titanflex.anadirEjercicioFragment
import kotlin.coroutines.coroutineContext


class itemMesRVAdapter(val items:List<itemMesRV>,private val listener: OnItemClickListener,private val contexto: Context): RecyclerView.Adapter<itemMesRVViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemMesRVViewHolder {
        val vista= LayoutInflater.from(parent.context).inflate(
            R.layout.item_ejercicio,
            parent,
            false
        )
        return itemMesRVViewHolder(vista)
    }

    override fun getItemCount(): Int=items.size

    override fun onBindViewHolder(holder: itemMesRVViewHolder, position: Int) {
        holder.nombre.text=items[position].nombre
        holder.desc.text=items[position].desc

        Glide.with(contexto)
            .load(items[position].image)
            .placeholder(R.drawable.no_hay_imagen) // opcional, una imagen de relleno mientras se carga la imagen
            .error(R.drawable.no_hay_imagen) // opcional, una imagen de error si falla la carga
            .into(holder.image)

        holder.card.setOnClickListener {
            println("El item seleccionado es ${items[position].id}")
            listener.onItemClick(items[position].id)
        }
    }
}