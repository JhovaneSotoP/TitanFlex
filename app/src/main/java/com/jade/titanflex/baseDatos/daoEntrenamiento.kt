package com.jade.titanflex.baseDatos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface daoEntrenamiento {
    @Query("SELECT * FROM entidadEntrenamiento")
    suspend fun extraerTodo():List<entidadEntrenamiento>
    @Query("SELECT * FROM entidadEntrenamiento WHERE id=:ID")
    suspend fun extraerPorID(ID:Int):List<entidadEntrenamiento>
    @Query("SELECT MAX(id) FROM entidadEntrenamiento")
    suspend fun ultimoID():Int
    @Insert
    suspend fun agregar(entrenamiento: entidadEntrenamiento)
    @Update
    suspend fun actualizar(entrenamiento: entidadEntrenamiento)

    @Delete
    suspend fun eliminar(entrenamiento: entidadEntrenamiento)
}