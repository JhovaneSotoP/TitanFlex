package com.jade.titanflex.baseDatos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface daoCategoria {
    @Query("SELECT * FROM entidadCategoria")
    suspend fun extraerTodo():List<entidadCategoria>
    @Query("SELECT * FROM entidadCategoria WHERE :id=id")
    suspend fun extraerSegunID(id:Int):List<entidadCategoria>
    @Query("SELECT * FROM entidadCategoria LIMIT :limite")
    fun obtenerCategoriasAleatorias( limite: Int): List<entidadCategoria>

    @Insert
    suspend fun agregar(categoria: entidadCategoria)
    @Update
    suspend fun actualizar(categoria: entidadCategoria)

    @Delete
    suspend fun eliminar(categoria: entidadCategoria)
}