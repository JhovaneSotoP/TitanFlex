package com.jade.titanflex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.jade.titanflex.baseDatos.dbPrincipal
import com.jade.titanflex.baseDatos.entidadEjerciciosEntrenamiento
import com.jade.titanflex.rv.itemMesRV
import com.jade.titanflex.rv.itemMesRVAdapter
import com.jade.titanflex.rv.itemMesRVViewModel
import kotlinx.coroutines.launch

class reproducirRutinaActivity : AppCompatActivity() {
    var id_rutina:Int = 0
    val itemViewModel: itemMesRVViewModel by viewModels()
    var ft=true

    val listaSeries= mutableListOf<entidadEjerciciosEntrenamiento>()

     var startTime = 0L
     var handler = Handler()
     lateinit var updateTimerRunnable: Runnable
     var tiempoEntrenamiento=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reproducir_rutina)
        id_rutina=intent!!.getIntExtra("id_rutina",0)

        supportFragmentManager.commit {
            replace<reproducir_a_Fragment>(R.id.contenedorFrameReproducirRutina)
            setReorderingAllowed(true)
        }

        updateTimerRunnable = object : Runnable {
            override fun run() {
                val elapsedMillis = SystemClock.uptimeMillis() - startTime
                val seconds = (elapsedMillis / 1000).toInt()
                val minutes = seconds / 60
                val displaySeconds = seconds % 60
                tiempoEntrenamiento=seconds
                handler.postDelayed(this, 1000)
            }
        }

        startTimer()

    }
    fun startTimer() {
        startTime = SystemClock.uptimeMillis()
        handler.post(updateTimerRunnable)
    }

    fun stopTimer() {
        handler.removeCallbacks(updateTimerRunnable)
    }

    fun actualizarDatos(adaptador:itemMesRVAdapter){
        if(ft){
            val dbPrincipal= Room.databaseBuilder(this, dbPrincipal::class.java,"user_data").build()

            if (id_rutina!=0){
                lifecycleScope.launch {
                    val ejercicios=dbPrincipal.ejerciciosRutinaDAO().extraerPorId(id_rutina)
                    for(n in 1..ejercicios.size){
                        try{
                            val ejercicio=dbPrincipal.ejerciciosDAO().extraerPorID(ejercicios[n-1].id_ejercicio)
                            val categorias=dbPrincipal.categoriaDAO().extraerSegunID(ejercicio[0].category)
                            var categoria=""
                            if (categorias.size>0){
                                categoria=categorias[0].nombre
                            }

                            val imagenes=dbPrincipal.multimediaDAO().extraerSegunID(ejercicio[0].id)
                            var imagen=""
                            if(imagenes.size>0){
                                imagen=imagenes[0].url
                            }
                            itemViewModel.elementos.add(itemMesRV(imagen,ejercicio[0].name,categoria,ejercicio[0].id))
                        }catch(ex:Exception){
                            println("${ex.message}")
                        }
                    }
                    adaptador.notifyDataSetChanged()
                }

            }
            ft=false
        }else{
            adaptador.notifyDataSetChanged()
        }

    }
}
