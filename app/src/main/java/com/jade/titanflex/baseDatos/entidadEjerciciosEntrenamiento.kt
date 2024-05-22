package com.jade.titanflex.baseDatos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class entidadEjerciciosEntrenamiento (
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val id_entrenamiento:Int=0,
    val id_ejercicio:Int,
    val pos:Int=0,
    val repeticiones:Int,
    val peso:Float,
    val unRep:Int=9,
    val unPeso:Int=1
)