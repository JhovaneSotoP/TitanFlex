package com.jade.titanflex.baseDatos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface daoEjercicios {
    @Query("SELECT * FROM entidadEjercicio")
    suspend fun extraerTodo():List<entidadEjercicio>

    @Query("SELECT COUNT(*) FROM entidadEjercicio")
    suspend fun numeroRegistros():Int
    @Query("SELECT MAX(id) FROM entidadEjercicio")
    suspend fun ultimoID():Int
    @Query("SELECT * FROM entidadEjercicio WHERE :cadena='' OR name LIKE '%' || :cadena || '%'")
    suspend fun extraerPorNombre(cadena:String):List<entidadEjercicio>
    @Insert
    suspend fun agregar(ejercicio: entidadEjercicio)
    @Update
    suspend fun actualizar(ejercicio: entidadEjercicio)

    @Delete
    suspend fun eliminar(ejercicio: entidadEjercicio)
}