package com.jade.titanflex.baseDatos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface daoMultimedia {
    @Query("SELECT * FROM entidadMultimedia")
    suspend fun extraerTodo():List<entidadMultimedia>

    @Query("SELECT * FROM entidadMultimedia WHERE :idEjercicioPrin=ejercicioPrincipal AND isImage=1")
    suspend fun extraerSegunID(idEjercicioPrin:Int):List<entidadMultimedia>
    @Insert
    suspend fun agregar(file: entidadMultimedia)
    @Update
    suspend fun actualizar(file: entidadMultimedia)

    @Delete
    suspend fun eliminar(file: entidadMultimedia)
}