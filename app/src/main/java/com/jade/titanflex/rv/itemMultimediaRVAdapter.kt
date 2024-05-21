package com.jade.titanflex.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jade.titanflex.OnItemClickListener
import com.jade.titanflex.R

class itemMultimediaRVAdapter (val items:List<itemMultimediaRV>,private val contexto: Context): RecyclerView.Adapter<itemMultimediaRVViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemMultimediaRVViewHolder {
        val vista= LayoutInflater.from(parent.context).inflate(
            R.layout.item_multimedia,
            parent,
            false
        )
        return itemMultimediaRVViewHolder(vista)
    }

    override fun getItemCount(): Int=items.size

    override fun onBindViewHolder(holder: itemMultimediaRVViewHolder, position: Int) {
        if(items[position].isImage){
            println("$position - ${items[position].url}")
            Glide.with(contexto)
                .load(items[position].url)
                .placeholder(R.drawable.no_hay_imagen) // opcional, una imagen de relleno mientras se carga la imagen
                .error(R.drawable.no_hay_imagen) // opcional, una imagen de error si falla la carga
                .into(holder.image)
            holder.video.isVisible=false
        }else{
            holder.video.isVisible=true
        }

    }
}