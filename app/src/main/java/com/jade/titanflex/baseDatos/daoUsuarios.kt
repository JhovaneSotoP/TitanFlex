package com.jade.titanflex.baseDatos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface daoUsuarios {
    @Query("SELECT * FROM entidadUsuarios")
    suspend fun extraerTodo():List<entidadUsuarios>

    @Insert
    suspend fun agregar(usuario: entidadUsuarios)
    @Update
    suspend fun actualizar(usuario: entidadUsuarios)

    @Delete
    suspend fun eliminar(usuario: entidadUsuarios)
}