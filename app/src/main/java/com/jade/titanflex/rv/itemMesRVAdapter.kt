package com.jade.titanflex.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jade.titanflex.R
import com.squareup.picasso.Picasso


class itemMesRVAdapter(val items:List<itemMesRV>): RecyclerView.Adapter<itemMesRVViewHolder>() {
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
            Picasso.get().load(items[position].image).into(holder.image)
            println(items[0].image)
        }catch (ex: Exception){
            println("Error")
        }
    }
}