package com.jade.titanflex.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.jade.titanflex.R
import com.jade.titanflex.listenerRutina

class itemRutinaSistemaRVAdapter (val items:List<itemRutinaSistema>, private val listener: listenerRutina, private val contexto: Context): RecyclerView.Adapter<itemRutinaSistemaRVViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemRutinaSistemaRVViewHolder {
        val vista= LayoutInflater.from(parent.context).inflate(
            R.layout.item_rutina_sistema,
            parent,
            false
        )
        return itemRutinaSistemaRVViewHolder(vista)
    }

    override fun getItemCount(): Int=items.size

    override fun onBindViewHolder(holder: itemRutinaSistemaRVViewHolder, position: Int) {
        holder.nombre.text=items[position].nombre
        holder.cant.text="${items[position].cant} ejercicios"
        holder.guardar.setOnClickListener {
            listener.eliminarRutina(items[position].id_rutina,position,contexto)
        }
        holder.iniciar.setOnClickListener {
            listener.reproducirRutina(items[position].id_rutina)
        }

        holder.color_Fondo.setBackgroundColor(contexto.getColor(items[position].color))
        holder.imagen_Fondo.setImageBitmap(contexto.getDrawable(items[position].imagen)!!.toBitmap())


    }
}