package com.jade.titanflex.baseDatos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface daoEquipo {
    @Query("SELECT * FROM entidadEquipo")
    suspend fun extraerTodo():List<entidadEquipo>
    @Query("SELECT * FROM entidadEquipo WHERE id=:id")
    suspend fun extraerPorID(id:Int):List<entidadEquipo>

    @Insert
    suspend fun agregar(equipo: entidadEquipo)
    @Update
    suspend fun actualizar(equipo: entidadEquipo)

    @Delete
    suspend fun eliminar(equipo: entidadEquipo)
}