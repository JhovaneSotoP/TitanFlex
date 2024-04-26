package com.jade.titanflex.baseDatos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class entidadMedidas (
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val id_medida: Int,
    val valor: Float,
    val dia: Int,
    val mes: Int,
    val anio: Int,
)