package com.jade.titanflex.rv

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jade.titanflex.R
import com.jade.titanflex.listenerSerie

class itemSerieRVAdapter (val items:List<itemSerie>,val listener:listenerSerie): RecyclerView.Adapter<itemSerieRVViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemSerieRVViewHolder {
        val vista= LayoutInflater.from(parent.context).inflate(
            R.layout.item_serie,
            parent,
            false
        )
        return itemSerieRVViewHolder(vista)
    }

    override fun getItemCount(): Int=items.size

    override fun onBindViewHolder(holder: itemSerieRVViewHolder,position: Int) {
        holder.serie.text="${position+1}"
        holder.peso.setText(items[position].pes.toString())
        holder.repeticion.setText(items[position].rep.toString())

        holder.serie.setOnClickListener {
            listener.eliminarItem(position)
        }

        holder.peso.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val texto=s.toString()
                if(texto.isNotEmpty()){
                    listener.actualizarPes(texto.toFloat(),holder.adapterPosition)
                }else{
                    holder.peso.setText("0.0")
                    holder.peso.setSelection(3)
                }
            }
        })


        holder.repeticion.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val texto=s.toString()
                if(texto.isNotEmpty()){
                    listener.actualizarRep(texto.toInt(),holder.adapterPosition)
                }else{
                    holder.repeticion.setText("0")
                    holder.repeticion.setSelection(1)
                }
            }
        })
    }
}