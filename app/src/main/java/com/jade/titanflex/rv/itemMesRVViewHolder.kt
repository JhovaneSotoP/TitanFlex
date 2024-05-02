package com.jade.titanflex.rv

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jade.titanflex.R

class itemMesRVViewHolder(vista: View): RecyclerView.ViewHolder(vista) {
    val image=vista.findViewById<ImageView>(R.id.ivImagenEjercicioItem)
    var nombre=vista.findViewById<TextView>(R.id.tvTituloEjercicio)
    var desc=vista.findViewById<TextView>(R.id.tvCategoriaEjercicio)
}