package com.jade.titanflex.baseDatos

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class entidadRutinaEjercicios (
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val id_rutina: Int,
    val id_ejercicio:Int
)