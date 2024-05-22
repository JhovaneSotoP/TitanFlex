package com.jade.titanflex.baseDatos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update



@Dao
interface daoRutinaEjercicios {
    @Query("SELECT * FROM entidadRutinaEjercicios")
    suspend fun extraerTodo():List<entidadRutinaEjercicios>

    @Query("SELECT COUNT(*) FROM entidadRutinaEjercicios WHERE id_rutina=:id")
    suspend fun numeroRegistros(id:Int):Int
    @Query("SELECT MAX(id) FROM entidadRutinaEjercicios")
    suspend fun ultimoID():Int
    @Query("DELETE FROM entidadRutinaEjercicios WHERE id_rutina=:id")
    suspend fun eliminarPorId(id:Int)
    @Query("SELECT * FROM entidadRutinaEjercicios WHERE id_rutina=:id")
    suspend fun extraerPorId(id:Int):List<entidadRutinaEjercicios>

    @Insert
    suspend fun agregar(ejercicios: entidadRutinaEjercicios)
    @Update
    suspend fun actualizar(ejercicios: entidadRutinaEjercicios)

    @Delete
    suspend fun eliminar(ejercicios: entidadRutinaEjercicios)
}