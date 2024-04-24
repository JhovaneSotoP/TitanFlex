package com.jade.titanflex.baseDatos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface daoRegistroAltura {
    @Query("SELECT * FROM entidadRegistroAltura")
    suspend fun extraerTodo():List<entidadRegistroAltura>

    @Insert
    suspend fun agregar(altura: entidadRegistroAltura)
    @Update
    suspend fun actualizar(altura: entidadRegistroAltura)

    @Delete
    suspend fun eliminar(altura: entidadRegistroAltura)
}