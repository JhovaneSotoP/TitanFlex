package com.jade.titanflex.baseDatos

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities=[entidadUsuarios::class,
        entidadMedidas::class,
        entidadUnidadMedida::class,
        entidadEjercicio::class,
        entidadEquipoEjercicio::class,
        entidadMusculoEjercicios::class,
        entidadMultimedia::class,
        entidadCategoria::class,
        entidadRutina::class,
        entidadRutinaEjercicios::class,
        entidadEquipo::class,
        entidadMusculo::class,
        entidadEntrenamiento::class,
        entidadEjerciciosEntrenamiento::class],
    version=12
)
abstract class dbPrincipal: RoomDatabase() {
    abstract fun usersDAO():daoUsuarios
    abstract fun medidasDAO():daoMedidas
    abstract fun unidadMedidaDAO():daoUnidadMedida
    abstract fun ejerciciosDAO():daoEjercicios
    abstract fun equipoEjerciciosDAO():daoEquipoEjercicio
    abstract fun musculosEjerciciosDAO():daoMusculosEjercicios
    abstract fun multimediaDAO():daoMultimedia
    abstract fun categoriaDAO():daoCategoria
    abstract fun rutinaDAO():daoRutina
    abstract fun ejerciciosRutinaDAO():daoRutinaEjercicios
    abstract fun equipoDAO():daoEquipo
    abstract fun musculoDAO():daoMusculo
    abstract fun enntrenamientoDAO():daoEntrenamiento
    abstract fun seriesDAO():daoEjerciciosEntrenamiento
}