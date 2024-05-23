package com.jade.titanflex.rv

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jade.titanflex.R

class itemHistorialRVViewHolder (vista: View): RecyclerView.ViewHolder(vista) {
    val fecha=vista.findViewById<TextView>(R.id.tvitemHistorialFecha)
    val tiempo=vista.findViewById<TextView>(R.id.tvitemHistorialTiempo)
    val ejercicios=vista.findViewById<TextView>(R.id.tvitemHistorialSeries)
    val carta=vista.findViewById<CardView>(R.id.cardHistorial)
}