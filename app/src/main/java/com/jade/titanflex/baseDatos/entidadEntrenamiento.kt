package com.jade.titanflex.baseDatos

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class entidadEntrenamiento (
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val duracion:Int,
    val dia:Int,
    val mes:Int,
    val anio:Int
)