package com.jade.titanflex.baseDatos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface daoRegistroPeso {
    @Query("SELECT * FROM entidadRegistroPeso")
    suspend fun extraerTodo():List<entidadRegistroPeso>

    @Insert
    suspend fun agregar(pesoNuevo: entidadRegistroPeso)
    @Update
    suspend fun actualizar(pesoActualizado: entidadRegistroPeso)

    @Delete
    suspend fun eliminar(peso: entidadRegistroPeso)
}