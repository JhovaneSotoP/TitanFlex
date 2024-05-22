package com.jade.titanflex.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jade.titanflex.OnItemClickListener
import com.jade.titanflex.R
import com.jade.titanflex.crearManiqui
import com.jade.titanflex.listenerRutina

class itemRutinasRVAdapter(val items:List<itemRutina>,private val listener:listenerRutina, private val contexto:Context): RecyclerView.Adapter<itemRutinaRVViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemRutinaRVViewHolder {
        val vista= LayoutInflater.from(parent.context).inflate(
            R.layout.item_rutina,
            parent,
            false
        )
        return itemRutinaRVViewHolder(vista)
    }

    override fun getItemCount(): Int=items.size

    override fun onBindViewHolder(holder: itemRutinaRVViewHolder, position: Int) {
        holder.nombre.text=items[position].nombre
        holder.cant.text="${items[position].cant} ejercicios"
        holder.elimininar.setOnClickListener {
            listener.eliminarRutina(items[position].id_rutina,position,contexto)
        }
        holder.iniciar.setOnClickListener {
            listener.reproducirRutina(items[position].id_rutina)
        }
        listener.agregarManiqui(contexto,holder.imagen_a,holder.imagen_b,items[position].muscPrin,items[position].muscSec)

    }
}