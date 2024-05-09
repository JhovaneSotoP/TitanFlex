package com.jade.titanflex.rv

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jade.titanflex.R

class itemMultimediaRVViewHolder(vista: View): RecyclerView.ViewHolder(vista) {
    val image=vista.findViewById<ImageView>(R.id.ivImagenItemMultimedia)
    var video=vista.findViewById<VideoView>(R.id.vvVideoItemMultimedia)
    }