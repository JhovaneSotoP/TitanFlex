package com.jade.titanflex

import android.content.Context

interface OnItemClickListener {
    fun onItemClick(position: Int)
}

interface listenerRutina{
    fun reproducirRutina(id:Int)
    fun eliminarRutina(id:Int,pos:Int,contexto: Context)
}

interface listenerSerie{
    fun actualizarRep(repe:Int,pos:Int)
    fun actualizarPes(peso:Float,pos:Int)
    fun eliminarItem(pos:Int)
}