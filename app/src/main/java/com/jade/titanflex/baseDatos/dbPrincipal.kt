package com.jade.titanflex.baseDatos

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities=[entidadUsuarios::class,entidadRegistroPeso::class,entidadRegistroAltura::class],
    version=3
)
abstract class dbPrincipal: RoomDatabase() {
    abstract fun usersDAO():daoUsuarios
    abstract fun registroPesoDAO():daoRegistroPeso
    abstract fun registroAlturaDAO():daoRegistroAltura
}