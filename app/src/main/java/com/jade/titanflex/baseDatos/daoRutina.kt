package com.jade.titanflex.baseDatos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface daoRutina {
    @Query("SELECT * FROM entidadRutina")
    suspend fun extraerTodo():List<entidadRutina>
    @Query("SELECT * FROM entidadRutina WHERE hechoPorUsuario=:creadoPorUsuario")
    suspend fun extraerPorEstado(creadoPorUsuario:Boolean):List<entidadRutina>
    @Query("SELECT * FROM entidadRutina WHERE id=:ID")
    suspend fun extraerPorID(ID:Int):List<entidadRutina>
    @Query("SELECT COUNT(*) FROM entidadRutina")
    suspend fun numeroRegistros():Int
    @Query("SELECT MAX(id) FROM entidadRutina")
    suspend fun ultimoID():Int
    @Query("DELETE FROM entidadRutina WHERE id=:id")
    suspend fun eliminarPorID(id:Int)
    @Insert
    suspend fun agregar(rutina: entidadRutina)
    @Update
    suspend fun actualizar(rutina: entidadRutina)

    @Delete
    suspend fun eliminar(rutina: entidadEjercicio)
}