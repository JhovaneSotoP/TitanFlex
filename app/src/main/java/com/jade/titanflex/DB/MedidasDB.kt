package com.jade.titanflex.DB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities=[Medidas::class],
    version=1
)
abstract class MedidasDB:RoomDatabase() {
    abstract fun medidasDAO():MedidasDAO
}