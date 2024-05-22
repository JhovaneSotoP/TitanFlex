package com.jade.titanflex.rv

import com.jade.titanflex.elementosMusculo

data class itemRutina (val nombre:String,val cant:Int,val id_rutina:Int,val muscPrin:List<elementosMusculo>,val muscSec:List<elementosMusculo>)