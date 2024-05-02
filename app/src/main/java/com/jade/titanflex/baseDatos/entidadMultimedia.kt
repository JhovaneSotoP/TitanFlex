package com.jade.titanflex.baseDatos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class entidadMultimedia (
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val url:String,
    val isImage:Boolean,
    val ejercicioPrincipal:Int
)