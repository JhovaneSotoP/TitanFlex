package com.jade.titanflex.baseDatos

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class entidadEjercicio (
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val category: Int,
    val description: String,
    val exercise_base: Int,
    val name: String,
    val hechoPorUsuario:Boolean=false
)