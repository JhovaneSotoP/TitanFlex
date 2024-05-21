package com.jade.titanflex.baseDatos

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class entidadEquipo (
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val nombre: String
)