package com.jade.titanflex.baseDatos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class entidadEquipoEjercicio (
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val num: Int,
    val ejercicioPrincipal:Int
)