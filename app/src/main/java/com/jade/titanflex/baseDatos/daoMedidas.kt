package com.jade.titanflex.baseDatos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface daoMedidas {
    @Query("SELECT * FROM entidadMedidas")
    suspend fun extraerTodo():List<entidadMedidas>

    @Insert
    suspend fun agregar(medida: entidadMedidas)
    @Update
    suspend fun actualizar(medida: entidadMedidas)

    @Delete
    suspend fun eliminar(medida: entidadMedidas)
}