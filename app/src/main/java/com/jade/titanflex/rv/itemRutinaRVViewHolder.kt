package com.jade.titanflex.rv

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jade.titanflex.R

class itemRutinaRVViewHolder (vista: View): RecyclerView.ViewHolder(vista) {
    val nombre=vista.findViewById<TextView>(R.id.tvItemRutinaNombre)
    val cant=vista.findViewById<TextView>(R.id.tvItemRutinaCantidad)
    val iniciar=vista.findViewById<CardView>(R.id.btIniciarRutina)
    val elimininar=vista.findViewById<CardView>(R.id.btEliminarRutina)
    val imagen_a=vista.findViewById<ImageView>(R.id.ivImagenRutina_A)
    val imagen_b=vista.findViewById<ImageView>(R.id.ivImagenRutina_B)
}