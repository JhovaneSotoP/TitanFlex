package com.jade.titanflex.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.jade.titanflex.OnItemClickListener
import com.jade.titanflex.R
import com.squareup.picasso.Picasso

class itemMultimediaRVAdapter (val items:List<itemMultimediaRV>): RecyclerView.Adapter<itemMultimediaRVViewHolder>() {
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
            try{
                if (items[position].url==""){
                    Picasso.get().load(R.drawable.no_hay_imagen).into(holder.image)
                }else{
                    Picasso.get().load(items[position].url).into(holder.image)
                }

                holder.video.isVisible=false

            }catch (ex: Exception){
                Picasso.get().load(R.drawable.no_hay_imagen).into(holder.image)
                println("error en $position con ${items[position].url}")
            }
        }else{
            holder.video.isVisible=true
        }

    }
}