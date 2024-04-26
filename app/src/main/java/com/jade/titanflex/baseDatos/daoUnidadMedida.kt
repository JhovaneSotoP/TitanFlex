package com.jade.titanflex.baseDatos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface daoUnidadMedida {
    @Query("SELECT * FROM entidadUnidadMedida")
    suspend fun extraerTodo():List<entidadUnidadMedida>

    @Insert
    suspend fun agregar(unidad: entidadUnidadMedida)
    @Update
    suspend fun actualizar(unidad: entidadUnidadMedida)

    @Delete
    suspend fun eliminar(unidad: entidadUnidadMedida)
}