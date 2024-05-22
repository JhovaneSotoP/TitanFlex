package com.jade.titanflex.baseDatos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface daoEjerciciosEntrenamiento {
    @Query("SELECT * FROM entidadEjerciciosEntrenamiento")
    suspend fun extraerTodo():List<entidadEjerciciosEntrenamiento>

    @Query("SELECT * FROM entidadEjerciciosEntrenamiento WHERE id_entrenamiento=:ID")
    suspend fun extraerPorEntrenamiento(ID:Int):List<entidadEjerciciosEntrenamiento>
    @Insert
    suspend fun agregar(serie: entidadEjerciciosEntrenamiento)
    @Update
    suspend fun actualizar(serie: entidadEjerciciosEntrenamiento)

    @Delete
    suspend fun eliminar(serie: entidadEjerciciosEntrenamiento)
}