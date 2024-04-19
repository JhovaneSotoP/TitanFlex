package com.jade.titanflex.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Medidas (
    @PrimaryKey val fecha:String,
    val peso: Float,
    val altura: Float
)