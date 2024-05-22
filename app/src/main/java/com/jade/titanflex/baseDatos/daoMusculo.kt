package com.jade.titanflex.baseDatos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface daoMusculo {
    @Query("SELECT * FROM entidadMusculo")
    suspend fun extraerTodo():List<entidadMusculo>
    @Query("SELECT * FROM entidadMusculo WHERE id=:id")
    suspend fun extraerPorID(id:Int):List<entidadMusculo>

    @Insert
    suspend fun agregar(musculo: entidadMusculo)
    @Update
    suspend fun actualizar(musculo: entidadMusculo)

    @Delete
    suspend fun eliminar(musculo: entidadMusculo)
}