package com.jade.titanflex.baseDatos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface daoEquipoEjercicio {
    @Query("SELECT * FROM entidadEquipoEjercicio")
    suspend fun extraerTodo():List<entidadEquipoEjercicio>
    @Query("SELECT * FROM entidadEquipoEjercicio WHERE ejercicioPrincipal=:id")
    suspend fun extraerPorID(id:Int):List<entidadEquipoEjercicio>

    @Insert
    suspend fun agregar(equipo: entidadEquipoEjercicio)
    @Update
    suspend fun actualizar(equipo: entidadEquipoEjercicio)

    @Delete
    suspend fun eliminar(equipo: entidadEquipoEjercicio)
}