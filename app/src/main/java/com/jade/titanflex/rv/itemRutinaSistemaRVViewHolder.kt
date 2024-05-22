package com.jade.titanflex.rv

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jade.titanflex.R

class itemRutinaSistemaRVViewHolder  (vista: View): RecyclerView.ViewHolder(vista) {
    val nombre=vista.findViewById<TextView>(R.id.tvItemRutinaSistemaNombre)
    val cant=vista.findViewById<TextView>(R.id.tvItemRutinaSistemaCantidad)
    val iniciar=vista.findViewById<CardView>(R.id.btIniciarRutinaSistema)
    val guardar=vista.findViewById<CardView>(R.id.btEliminarRutinaSistema)
    val imagen_Fondo=vista.findViewById<ImageView>(R.id.ivFondoRutinaSistema)
    val color_Fondo=vista.findViewById<ImageView>(R.id.ivColorRutinaSistema)
}