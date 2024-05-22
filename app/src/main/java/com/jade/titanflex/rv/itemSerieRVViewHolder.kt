package com.jade.titanflex.rv

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jade.titanflex.R

class itemSerieRVViewHolder (vista: View): RecyclerView.ViewHolder(vista) {
    val serie=vista.findViewById<TextView>(R.id.tvItemSerieTitulo)
    val repeticion=vista.findViewById<EditText>(R.id.tvItemSerieRepeticion)
    val peso=vista.findViewById<EditText>(R.id.tvItemSeriePeso)
}