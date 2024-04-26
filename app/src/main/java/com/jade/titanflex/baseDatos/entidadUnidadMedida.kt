package com.jade.titanflex.baseDatos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class entidadUnidadMedida(
    @PrimaryKey val id:Int,
    val nombre: String
)