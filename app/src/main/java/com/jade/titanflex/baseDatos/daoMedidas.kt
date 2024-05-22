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
    @Query("SELECT * FROM entidadMedidas WHERE id_medida=1")
    suspend fun extraerTodosPesos():List<entidadMedidas>
    @Query("SELECT * FROM entidadMedidas WHERE id_medida=:id_medida AND id=:id")
    suspend fun extraerPeso(id:Int,id_medida: Int):List<entidadMedidas>
    @Query("SELECT MAX(id) FROM entidadMedidas WHERE id_medida=:id_medida")
    suspend fun extraerMaxIDMedida(id_medida:Int):Int
    @Query("SELECT * FROM entidadMedidas WHERE id_medida=1 AND dia=:dia AND mes=:mes AND anio=:anio")
    suspend fun extraerPesoFecha(dia:Int,mes:Int,anio:Int):List<entidadMedidas>

    @Insert
    suspend fun agregar(medida: entidadMedidas)
    @Update
    suspend fun actualizar(medida: entidadMedidas)

    @Delete
    suspend fun eliminar(medida: entidadMedidas)
}