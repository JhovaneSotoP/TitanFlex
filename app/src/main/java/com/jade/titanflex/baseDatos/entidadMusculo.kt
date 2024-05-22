package com.jade.titanflex.baseDatos

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class entidadMusculo (
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val nombre: String,
    val nombre_en:String,
    val is_front:Boolean=true,
    val imageMain:String,
    val imageSecondary:String
)