package com.jade.titanflex

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jade.titanflex.baseDatos.dbPrincipal
import com.jade.titanflex.baseDatos.entidadEntrenamiento
import com.jade.titanflex.rv.itemMesRVViewModel
import com.jade.titanflex.rv.itemRutinaSistema
import com.jade.titanflex.rv.itemRutinaSistemaRVAdapter
import com.jade.titanflex.rv.itemRutinasSistemaRVViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit


class vistaPrincipal : AppCompatActivity() {
    var cadena: String ="Inicio"
    lateinit var ajustes: ImageView
    lateinit var titulo:TextView

    val itemViewModel: itemRutinasSistemaRVViewModel by viewModels()



    @RequiresApi(Build.VERSION_CODES.O)
    val fecha= LocalDate.now()
    var maxRacha:Int = 0
    var racha:Int=0

    val ejerciciosMes= mutableListOf<elementoGrafica>()
    val repeticionesMes= mutableListOf<elementoGrafica>()
    val volumenMes= mutableListOf<elementoGrafica>()
    val tiempoMes= mutableListOf<elementoGrafica>()


    private lateinit var navigator:BottomNavigationView
    private val mOnNavMenu=BottomNavigationView.OnNavigationItemSelectedListener{ item ->
        when(item.itemId){
            R.id.inicioFragment->{
                cadena="Inicio"
                titulo.setText(cadena)
                supportFragmentManager.commit {
                    replace<InicioFragment>(R.id.contenedorFrame)
                    setReorderingAllowed(true)
                    //addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.rutinasFragment->{
                cadena="Rutinas"
                titulo.setText(cadena)
                supportFragmentManager.commit {
                    replace<rutinasFragment>(R.id.contenedorFrame)
                    setReorderingAllowed(true)
                    //addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.estadisticasFragment->{
                cadena="Estadisticas"
                titulo.setText(cadena)
                supportFragmentManager.commit {
                    replace<estadisticasFragment>(R.id.contenedorFrame)
                    setReorderingAllowed(true)
                    //addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
        }

    false
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_principal)

        actualizarRutinasRecomendadas()

        titulo=findViewById(R.id.tituloVentana)
        ajustes=findViewById(R.id.btAjustes)
        ajustes.setOnClickListener {
            val vista= Intent(this,ActividadSecundaria::class.java)
            vista.putExtra("vista",ajustesFragment::class.java)
            vista.putExtra("titulo","Ajustes")
            startActivity(vista)
        }

        navigator=findViewById(R.id.menuPrincipal)
        navigator.setOnNavigationItemSelectedListener(mOnNavMenu)

        titulo.setText(cadena)
        supportFragmentManager.commit {
            replace<InicioFragment>(R.id.contenedorFrame)
            setReorderingAllowed(true)
            //addToBackStack("replacement")
        }
        actualizarData()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun actualizarData(){
        lifecycleScope.launch {
            val dbPrincipal= Room.databaseBuilder(this@vistaPrincipal, dbPrincipal::class.java,"user_data").build()



            var mesAtras=fecha.minusDays(30)

            val entrenamientos=dbPrincipal.enntrenamientoDAO().obtenerRegistrosPosterioresA(mesAtras.dayOfMonth,mesAtras.monthValue,mesAtras.year)

            var temp=-1
            var position=0

            for (entremaniento in entrenamientos){
                var id_temp=-1

                var ejercicios=0
                var volumen=0.0f
                var repeticiones=0.0f

                val series=dbPrincipal.seriesDAO().extraerPorEntrenamiento(entremaniento.id)
                for (serie in series){
                    volumen+=serie.peso
                    repeticiones+=serie.repeticiones
                    if(id_temp!=serie.id_ejercicio){
                        ejercicios+=1
                        id_temp=serie.id_ejercicio
                    }
                }



                if (temp!=entremaniento.dia){

                    while(entremaniento.dia!=mesAtras.dayOfMonth ){
                        ejerciciosMes.add(elementoGrafica(position.toFloat(),0.0f))
                        repeticionesMes.add(elementoGrafica(position.toFloat(),0.0f))
                        volumenMes.add(elementoGrafica(position.toFloat(),0.0f))
                        tiempoMes.add(elementoGrafica(position.toFloat(),0.0f))
                        mesAtras=mesAtras.plusDays(1)
                        position+=1
                    }

                    ejerciciosMes.add(elementoGrafica(position.toFloat(),ejercicios.toFloat()))
                    repeticionesMes.add(elementoGrafica(position.toFloat(),repeticiones))
                    volumenMes.add(elementoGrafica(position.toFloat(),volumen))
                    tiempoMes.add(elementoGrafica(position.toFloat(),(entremaniento.duracion/60).toFloat()))
                    mesAtras=mesAtras.plusDays(1)
                    temp=entremaniento.dia
                    position+=1
                    println("$position")
                }else{
                    ejerciciosMes[ejerciciosMes.size-1]= elementoGrafica(ejerciciosMes[ejerciciosMes.size-1].x,ejerciciosMes[ejerciciosMes.size-1].y+ejercicios)
                    repeticionesMes[repeticionesMes.size-1]= elementoGrafica(repeticionesMes[repeticionesMes.size-1].x,repeticionesMes[repeticionesMes.size-1].y+repeticiones)
                    volumenMes[volumenMes.size-1]= elementoGrafica(volumenMes[volumenMes.size-1].x,volumenMes[volumenMes.size-1].y+volumen)
                    tiempoMes[tiempoMes.size-1]= elementoGrafica(tiempoMes[tiempoMes.size-1].x,tiempoMes[tiempoMes.size-1].y+(entremaniento.duracion/60))
                }
            }
            while(position<=30 ){
                ejerciciosMes.add(elementoGrafica(position.toFloat(),0.0f))
                repeticionesMes.add(elementoGrafica(position.toFloat(),0.0f))
                volumenMes.add(elementoGrafica(position.toFloat(),0.0f))
                tiempoMes.add(elementoGrafica(position.toFloat(),0.0f))
                mesAtras=mesAtras.plusDays(1)
                position+=1
                println("$position")}

            extraerRacha()


        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun extraerRacha() {
        val dbPrincipal= Room.databaseBuilder(this@vistaPrincipal, dbPrincipal::class.java,"user_data").build()
        var entrenamientos=dbPrincipal.enntrenamientoDAO().extraerTodo()
        entrenamientos=entrenamientos.reversed()
        var mayRacha=0
        var temp=0
        var bandera=true

        try{
            val entrenamiento_temp=entrenamientos[0]
            for (n in 1..entrenamientos.size-1){
                if(sonFechasConsecutivas(entrenamiento_temp,entrenamientos[n])){
                    if (bandera){
                        racha+=1
                    }
                    temp+=1
                }else{
                    if(sonFechasIguales(entrenamiento_temp,entrenamientos[n])){

                    }else{
                        bandera=false
                        if(mayRacha<temp){
                            mayRacha=temp
                        }
                        temp=0
                    }
                }
            }
            maxRacha=mayRacha
        }catch(ex:Exception){

        }

        println("Mayor racha ${maxRacha}")
        println("Racha ${racha}")
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun sonFechasConsecutivas(r1: entidadEntrenamiento, r2: entidadEntrenamiento): Boolean {
        val fecha1 = LocalDate.of(r1.anio, r1.mes, r1.dia)
        val fecha2 = LocalDate.of(r2.anio, r2.mes, r2.dia)
        if(ChronoUnit.DAYS.between(fecha1, fecha2).toInt()==1||ChronoUnit.DAYS.between(fecha1, fecha2).toInt()==-1){
            println("Si entran ${r1.dia} ${r2.dia}")
            return true
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sonFechasIguales(r1: entidadEntrenamiento, r2: entidadEntrenamiento): Boolean {
        val fecha1 = LocalDate.of(r1.anio, r1.mes, r1.dia)
        val fecha2 = LocalDate.of(r2.anio, r2.mes, r2.dia)
        if(ChronoUnit.DAYS.between(fecha1, fecha2).toInt()==0){
            return true
        }
        return false
    }

    //adapter:itemRutinaSistemaRVAdapter
    fun actualizarRutinasRecomendadas(){
        lifecycleScope.launch {
            val dbPrincipal= Room.databaseBuilder(this@vistaPrincipal, dbPrincipal::class.java,"user_data").build()

            val lista_colores= mutableListOf<Int>()
            lista_colores.add(R.color.azul_a)
            lista_colores.add(R.color.purpura_a)
            lista_colores.add(R.color.amarillo_a)
            lista_colores.add(R.color.rosa_a)

            val lista_imagenes= mutableListOf<Int>()
            lista_imagenes.add(R.drawable.rv_1)
            lista_imagenes.add(R.drawable.rv_2)
            lista_imagenes.add(R.drawable.rv_3)
            lista_imagenes.add(R.drawable.rv_4)
            lista_imagenes.add(R.drawable.rv_5)
            lista_imagenes.add(R.drawable.rv_6)
            lista_imagenes.add(R.drawable.rv_7)
            lista_imagenes.add(R.drawable.rv_8)
            lista_imagenes.add(R.drawable.rv_9)
            lista_imagenes.add(R.drawable.rv_10)
            lista_imagenes.add(R.drawable.rv_11)
            lista_imagenes.add(R.drawable.rv_12)
            lista_imagenes.add(R.drawable.rv_13)


            var temp=0

            val rutinas=dbPrincipal.rutinaDAO().extraerPorEstado(false)
            itemViewModel.elementos.clear()

            for (n in 0..rutinas.size-1){
                val ejercicios=dbPrincipal.ejerciciosRutinaDAO().extraerPorId(rutinas[n].id)
                itemViewModel.elementos.add(itemRutinaSistema(rutinas[n].nombre,ejercicios.size,rutinas[n].id,lista_imagenes.random(),lista_colores[temp]))
                temp+=1
                if(temp>lista_colores.size-1){
                    temp=0
                }
            }
            //adapter.notifyDataSetChanged()
        }
    }

}
