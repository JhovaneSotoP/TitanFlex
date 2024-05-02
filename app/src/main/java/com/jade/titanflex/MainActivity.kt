package com.jade.titanflex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.jade.titanflex.api.RetrofitFactory
import com.jade.titanflex.baseDatos.dbPrincipal
import com.jade.titanflex.baseDatos.entidadCategoria
import com.jade.titanflex.baseDatos.entidadEjercicio
import com.jade.titanflex.baseDatos.entidadEquipoEjercicio
import com.jade.titanflex.baseDatos.entidadMultimedia
import com.jade.titanflex.baseDatos.entidadMusculoEjercicios
import com.jade.titanflex.baseDatos.entidadUnidadMedida
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbPrincipal= Room.databaseBuilder(this@MainActivity, dbPrincipal::class.java,"user_data").build()
        val boton=findViewById<Button>(R.id.botonInicio)
        //Agregar datos iniciales
        lifecycleScope.launch {
            boton.isEnabled=false

            //Agregar unidades de medida
            if(dbPrincipal.unidadMedidaDAO().extraerTodo().isEmpty()){
                dbPrincipal.unidadMedidaDAO().agregar(unidad = entidadUnidadMedida(1,"kg"))
                dbPrincipal.unidadMedidaDAO().agregar(unidad = entidadUnidadMedida(2,"lb"))
                dbPrincipal.unidadMedidaDAO().agregar(unidad = entidadUnidadMedida(3,"km"))
                dbPrincipal.unidadMedidaDAO().agregar(unidad = entidadUnidadMedida(4,"mi"))
                dbPrincipal.unidadMedidaDAO().agregar(unidad = entidadUnidadMedida(5,"cm"))
                dbPrincipal.unidadMedidaDAO().agregar(unidad = entidadUnidadMedida(6,"in"))
            }

            //Agregar categorias
            if(dbPrincipal.categoriaDAO().extraerTodo().isEmpty()){
                dbPrincipal.categoriaDAO().agregar(categoria = entidadCategoria(10, "Abs"))
                dbPrincipal.categoriaDAO().agregar(categoria = entidadCategoria(8, "Brazos"))
                dbPrincipal.categoriaDAO().agregar(categoria = entidadCategoria(12, "Espalda"))
                dbPrincipal.categoriaDAO().agregar(categoria = entidadCategoria(14, "Pantorrillas"))
                dbPrincipal.categoriaDAO().agregar(categoria = entidadCategoria(15, "Cardio"))
                dbPrincipal.categoriaDAO().agregar(categoria = entidadCategoria(11, "Pecho"))
                dbPrincipal.categoriaDAO().agregar(categoria = entidadCategoria(9, "Piernas"))
                dbPrincipal.categoriaDAO().agregar(categoria = entidadCategoria(13, "Hombros"))
            }

            val call=RetrofitFactory.getRetrofit()
            //val salida=call.extraerEjercicios(idLenguaje = 4)
            //Toast.makeText(this@MainActivity,"${salida.results.size}",Toast.LENGTH_SHORT).show()

           try {
               var ejercicios=call.extraerEjercicios()
               val maximo=ejercicios.count
               if(dbPrincipal.ejerciciosDAO().numeroRegistros()<maximo){
                   var offset=0
                   while (offset<maximo){
                       ejercicios=call.extraerEjercicios(inicioConteo = offset)
                       for(n in 1..ejercicios.results.size){
                           try {


                               val ejercicio=entidadEjercicio(category = ejercicios.results[n-1].category,
                                   description=ejercicios.results[n-1].description,
                                   exercise_base=ejercicios.results[n-1].exercise_base,
                                   name=ejercicios.results[n-1].name)
                               dbPrincipal.ejerciciosDAO().agregar(ejercicio)

                               val id=dbPrincipal.ejerciciosDAO().ultimoID()

                               //agregar MUSCULOS DE CADA EJERCICIO
                               for(k in 1..ejercicios.results[n-1].equipment.size){
                                   dbPrincipal.equipoEjerciciosDAO().agregar(equipo= entidadEquipoEjercicio(num = ejercicios.results[n-1].equipment[k-1],
                                       ejercicioPrincipal = id))
                               }

                               for(k in 1..ejercicios.results[n-1].muscles.size){
                                   dbPrincipal.musculosEjerciciosDAO().agregar(musculos = entidadMusculoEjercicios(num=ejercicios.results[n-1].muscles[k-1],
                                       principal = true,
                                       ejercicioPrincipal = id))
                               }

                               for(k in 1..ejercicios.results[n-1].muscles_secondary.size){
                                   dbPrincipal.musculosEjerciciosDAO().agregar(musculos = entidadMusculoEjercicios(num=ejercicios.results[n-1].muscles_secondary[k-1],
                                       principal = false,
                                       ejercicioPrincipal = id))
                               }



                               val imagen=call.extraerImagenes(idEjercicioBase = ejercicios.results[n-1].exercise_base)
                               for (k in 1..imagen.results.size){
                                   dbPrincipal.multimediaDAO().agregar(file= entidadMultimedia(url=imagen.results[k-1].image,
                                       isImage = true,
                                       ejercicioPrincipal = id))
                               }

                           }catch(ex:Exception){
                               Toast.makeText(this@MainActivity,"${ex.message}",Toast.LENGTH_LONG).show()
                           }
                       }
                       offset += 20
                   }
               }
           }catch(ex:Exception){
               Toast.makeText(this@MainActivity,"${ex.message}",Toast.LENGTH_LONG).show()
           }



            boton.isEnabled=true
        }
        //Fin de agregar datos iniciales

        boton.setOnClickListener{
            lifecycleScope.launch{
                try {
                    val salida=dbPrincipal.usersDAO().extraerTodo()
                    var vista= Intent(this@MainActivity,formularioInicial::class.java)
                    if(salida.size>0){
                        Intent(this@MainActivity, vistaPrincipal::class.java).also { vista = it }
                    }
                    startActivity(vista)
                    finish()
                }catch (ex:Exception){
                    println("${ex.message}")
                }
            }

        }
    }
}
