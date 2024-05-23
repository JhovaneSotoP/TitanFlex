package com.jade.titanflex

import android.content.Context
import android.widget.ImageView

interface OnItemClickListener {
    fun onItemClick(position: Int)
}

interface listenerRutina{
    fun reproducirRutina(id:Int)
    fun eliminarRutina(id:Int,pos:Int,contexto: Context)
    fun agregarManiqui(context: Context,
                       image_a: ImageView,
                       image_b: ImageView,
                       prin: List<elementosMusculo>,
                       sec: List<elementosMusculo>)
}

interface listenerSerie{
    fun actualizarRep(repe:Int,pos:Int)
    fun actualizarPes(peso:Float,pos:Int)
    fun eliminarItem(pos:Int)
}

interface listenerHistorial{
    fun mostrarEjercicios(textHTML:String)
}
