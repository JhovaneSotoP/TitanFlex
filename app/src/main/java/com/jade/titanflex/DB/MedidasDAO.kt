package com.jade.titanflex.DB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MedidasDAO {
    @Query("SELECT * FROM Medidas")
    suspend fun extraerTodo():List<Medidas>

    @Insert
    suspend fun agregar(medida:Medidas)
    @Update
    suspend fun actualizar(medida:Medidas)

    @Delete
    suspend fun eliminar(medida:Medidas)
}