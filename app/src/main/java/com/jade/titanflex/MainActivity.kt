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
import com.jade.titanflex.baseDatos.entidadEquipo
import com.jade.titanflex.baseDatos.entidadEquipoEjercicio
import com.jade.titanflex.baseDatos.entidadMultimedia
import com.jade.titanflex.baseDatos.entidadMusculo
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
                dbPrincipal.unidadMedidaDAO().agregar(unidad = entidadUnidadMedida(7,"seg"))
                dbPrincipal.unidadMedidaDAO().agregar(unidad = entidadUnidadMedida(8,"minutos"))
                dbPrincipal.unidadMedidaDAO().agregar(unidad = entidadUnidadMedida(9,"repeticiones"))
            }
            if(dbPrincipal.equipoDAO().extraerTodo().isEmpty()){
                dbPrincipal.equipoDAO().agregar(equipo= entidadEquipo(1, "Barra"))
                dbPrincipal.equipoDAO().agregar(equipo= entidadEquipo(8, "Banco"))
                dbPrincipal.equipoDAO().agregar(equipo= entidadEquipo(3, "Mancuerna"))
                dbPrincipal.equipoDAO().agregar(equipo= entidadEquipo(4, "Colchoneta de gimnasio"))
                dbPrincipal.equipoDAO().agregar(equipo= entidadEquipo(9, "Banco inclinado"))
                dbPrincipal.equipoDAO().agregar(equipo= entidadEquipo(10, "Pesas rusas"))
                dbPrincipal.equipoDAO().agregar(equipo= entidadEquipo(6, "Barra de dominadas"))
                dbPrincipal.equipoDAO().agregar(equipo= entidadEquipo(2, "Barra Z"))
                dbPrincipal.equipoDAO().agregar(equipo= entidadEquipo(5, "Pelota suiza"))
                dbPrincipal.equipoDAO().agregar(equipo= entidadEquipo(7, "Ninguno (ejercicio de peso corporal)"))

            }
            if(dbPrincipal.musculoDAO().extraerTodo().isEmpty()){
                dbPrincipal.musculoDAO().agregar(musculo=entidadMusculo(id=2, nombre="Deltoides anterior", nombre_en="Hombros", is_front=true, imageMain="https://wger.de/static/images/muscles/main/muscle-2.svg", imageSecondary="https://wger.de/static/images/muscles/secondary/muscle-2.svg"))
                dbPrincipal.musculoDAO().agregar(musculo=entidadMusculo(id=1, nombre="Bíceps braquial", nombre_en="Biceps", is_front=true, imageMain="https://wger.de/static/images/muscles/main/muscle-1.svg", imageSecondary="https://wger.de/static/images/muscles/secondary/muscle-1.svg"))
                dbPrincipal.musculoDAO().agregar(musculo=entidadMusculo(id=11, nombre="Bíceps femoral", nombre_en="Isquiotibiales", is_front=false, imageMain="https://wger.de/static/images/muscles/main/muscle-11.svg", imageSecondary="https://wger.de/static/images/muscles/secondary/muscle-11.svg"))
                dbPrincipal.musculoDAO().agregar(musculo=entidadMusculo(id=13, nombre="Braquial", nombre_en="", is_front=true, imageMain="https://wger.de/static/images/muscles/main/muscle-13.svg", imageSecondary="https://wger.de/static/images/muscles/secondary/muscle-13.svg"))
                dbPrincipal.musculoDAO().agregar(musculo=entidadMusculo(id=7, nombre="Gemelos", nombre_en="Pantorrillas", is_front=false, imageMain="https://wger.de/static/images/muscles/main/muscle-7.svg", imageSecondary="https://wger.de/static/images/muscles/secondary/muscle-7.svg"))
                dbPrincipal.musculoDAO().agregar(musculo=entidadMusculo(id=8, nombre="Glúteo mayor", nombre_en="Glúteos", is_front=false, imageMain="https://wger.de/static/images/muscles/main/muscle-8.svg", imageSecondary="https://wger.de/static/images/muscles/secondary/muscle-8.svg"))
                dbPrincipal.musculoDAO().agregar(musculo=entidadMusculo(id=12, nombre="Dorsal ancho", nombre_en="Lats", is_front=false, imageMain="https://wger.de/static/images/muscles/main/muscle-12.svg", imageSecondary="https://wger.de/static/images/muscles/secondary/muscle-12.svg"))
                dbPrincipal.musculoDAO().agregar(musculo=entidadMusculo(id=14, nombre="Oblicuo externo del abdomen", nombre_en="", is_front=true, imageMain="https://wger.de/static/images/muscles/main/muscle-14.svg", imageSecondary="https://wger.de/static/images/muscles/secondary/muscle-14.svg"))
                dbPrincipal.musculoDAO().agregar(musculo=entidadMusculo(id=4, nombre="Pectoral mayor", nombre_en="Pecho", is_front=true, imageMain="https://wger.de/static/images/muscles/main/muscle-4.svg", imageSecondary="https://wger.de/static/images/muscles/secondary/muscle-4.svg"))
                dbPrincipal.musculoDAO().agregar(musculo=entidadMusculo(id=10, nombre="Cuádriceps femoral", nombre_en="Cuádriceps", is_front=true, imageMain="https://wger.de/static/images/muscles/main/muscle-10.svg", imageSecondary="https://wger.de/static/images/muscles/secondary/muscle-10.svg"))
                dbPrincipal.musculoDAO().agregar(musculo=entidadMusculo(id=6, nombre="Recto abdominal", nombre_en="Abdominales", is_front=true, imageMain="https://wger.de/static/images/muscles/main/muscle-6.svg", imageSecondary="https://wger.de/static/images/muscles/secondary/muscle-6.svg"))
                dbPrincipal.musculoDAO().agregar(musculo=entidadMusculo(id=3, nombre="Serrato anterior", nombre_en="", is_front=true, imageMain="https://wger.de/static/images/muscles/main/muscle-3.svg", imageSecondary="https://wger.de/static/images/muscles/secondary/muscle-3.svg"))
                dbPrincipal.musculoDAO().agregar(musculo=entidadMusculo(id=15, nombre="Sóleo", nombre_en="", is_front=false, imageMain="https://wger.de/static/images/muscles/main/muscle-15.svg", imageSecondary="https://wger.de/static/images/muscles/secondary/muscle-15.svg"))
                dbPrincipal.musculoDAO().agregar(musculo=entidadMusculo(id=9, nombre="Trapecio", nombre_en="", is_front=false, imageMain="https://wger.de/static/images/muscles/main/muscle-9.svg", imageSecondary="https://wger.de/static/images/muscles/secondary/muscle-9.svg"))
                dbPrincipal.musculoDAO().agregar(musculo=entidadMusculo(id=5, nombre="Tríceps braquial", nombre_en="Tríceps", is_front=false, imageMain="https://wger.de/static/images/muscles/main/muscle-5.svg", imageSecondary="https://wger.de/static/images/muscles/secondary/muscle-5.svg"))

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
