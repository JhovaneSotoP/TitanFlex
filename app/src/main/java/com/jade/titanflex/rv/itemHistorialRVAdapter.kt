package com.jade.titanflex.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jade.titanflex.R
import com.jade.titanflex.listenerHistorial

class itemHistorialRVAdapter (val items:List<itemHistorial>, val listener:listenerHistorial): RecyclerView.Adapter<itemHistorialRVViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemHistorialRVViewHolder {
        val vista= LayoutInflater.from(parent.context).inflate(
            R.layout.item_historial,
            parent,
            false
        )
        return itemHistorialRVViewHolder(vista)
    }

    override fun getItemCount(): Int=items.size

    override fun onBindViewHolder(holder: itemHistorialRVViewHolder, position: Int) {
        holder.fecha.setText(items[position].fecha)


        holder.tiempo.setText(String.format("%02d:%02d", items[position].tiempo/60, items[position].tiempo%60))
        holder.ejercicios.setText("${items[position].ejercicios} ejercicios")

        holder.carta.setOnClickListener {
            listener.mostrarEjercicios(items[position].textoPush)
        }
    }
}