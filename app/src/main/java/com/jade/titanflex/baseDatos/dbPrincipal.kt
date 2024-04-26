package com.jade.titanflex.baseDatos

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities=[entidadUsuarios::class,entidadMedidas::class,entidadUnidadMedida::class],
    version=5
)
abstract class dbPrincipal: RoomDatabase() {
    abstract fun usersDAO():daoUsuarios
    abstract fun medidasDAO():daoMedidas
    abstract fun unidadMedidaDAO():daoUnidadMedida
}