package com.jade.titanflex.baseDatos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface daoMusculosEjercicios {
    @Query("SELECT * FROM entidadMusculoEjercicios")
    suspend fun extraerTodo():List<entidadMusculoEjercicios>
    @Query("SELECT * FROM entidadMusculoEjercicios WHERE ejercicioPrincipal=:ID AND principal=1")
    suspend fun extraerPrimarioPorID(ID:Int):List<entidadMusculoEjercicios>
    @Query("SELECT * FROM entidadMusculoEjercicios WHERE ejercicioPrincipal=:ID AND principal=0")
    suspend fun extraerSecundarioPorID(ID:Int):List<entidadMusculoEjercicios>
    @Insert
    suspend fun agregar(musculos: entidadMusculoEjercicios)
    @Update
    suspend fun actualizar(musculos: entidadMusculoEjercicios)

    @Delete
    suspend fun eliminar(musculos: entidadMusculoEjercicios)
}