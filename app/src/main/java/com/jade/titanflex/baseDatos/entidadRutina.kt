package com.jade.titanflex.baseDatos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class entidadRutina (
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val nombre: String,
    val segDesc:Int,
    val hechoPorUsuario:Boolean=true
)