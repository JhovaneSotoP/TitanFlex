package com.jade.titanflex.baseDatos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class entidadRegistroPeso (
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val peso: Float,
    val dia: Int,
    val mes: Int,
    val anio: Int
)