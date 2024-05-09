package com.jade.titanflex.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.jade.titanflex.OnItemClickListener
import com.jade.titanflex.R
import com.jade.titanflex.anadirEjercicioFragment
import com.squareup.picasso.Picasso
import kotlin.coroutines.coroutineContext


class itemMesRVAdapter(val items:List<itemMesRV>,private val listener: OnItemClickListener): RecyclerView.Adapter<itemMesRVViewHolder>() {
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
        try{
            println(items[0].image)
            Picasso.get().load(items[position].image).into(holder.image)

        }catch (ex: Exception){
            Picasso.get().load(R.drawable.no_hay_imagen).into(holder.image)
            println("error en $position con ${items[position].image}")
        }
        holder.card.setOnClickListener {
            println("El item seleccionado es ${items[position].id}")
            listener.onItemClick(items[position].id)
        }
    }
}