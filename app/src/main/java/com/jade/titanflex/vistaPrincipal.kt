package com.jade.titanflex

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jade.titanflex.baseDatos.dbPrincipal
import kotlinx.coroutines.launch
import java.time.LocalDate


class vistaPrincipal : AppCompatActivity() {
    var cadena: String ="Inicio"
    lateinit var ajustes: ImageView
    lateinit var titulo:TextView



    @RequiresApi(Build.VERSION_CODES.O)
    val fecha= LocalDate.now()

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
        }
    }


}
