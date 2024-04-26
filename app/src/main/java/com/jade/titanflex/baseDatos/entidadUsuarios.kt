package com.jade.titanflex.baseDatos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class entidadUsuarios (
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val sexo: Int,
    val diaNacimiento: Int,
    val mesNacimiento: Int,
    val anioNacimiento: Int,
    val unidadMasa:Int,
    val unidadDistancia:Int,
    val unidadMedidaCorporal:Int
)