package com.jade.titanflex

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.jade.titanflex.baseDatos.entidadRutina
import com.jade.titanflex.baseDatos.entidadRutinaEjercicios
import com.jade.titanflex.baseDatos.entidadUnidadMedida
import com.jade.titanflex.baseDatos.entidadUsuarios
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var barra:ProgressBar
    lateinit var label: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbPrincipal= Room.databaseBuilder(this@MainActivity, dbPrincipal::class.java,"user_data").build()
        barra=findViewById(R.id.progressBarInicio)
        label=findViewById(R.id.tvInicioProgreso)
        //Agregar datos iniciales
        lifecycleScope.launch {
            barra.setProgress(0)
            label.setText("Iniciando aplicación")
            val fecha= LocalDate.now()

            //Agregar unidades de medida
            if(dbPrincipal.unidadMedidaDAO().extraerTodo().isEmpty()){
                label.setText("Agregando unidades de medida...")
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
            barra.setProgress(15)
            if(dbPrincipal.equipoDAO().extraerTodo().isEmpty()){
                label.setText("Agregando equipo...")
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
            barra.setProgress(30)
            if(dbPrincipal.musculoDAO().extraerTodo().isEmpty()){
                label.setText("Agregando músculos...")
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
            barra.setProgress(45)
            //Agregar categorias
            if(dbPrincipal.categoriaDAO().extraerTodo().isEmpty()){
                label.setText("Agregando categorias...")
                dbPrincipal.categoriaDAO().agregar(categoria = entidadCategoria(10, "Abs"))
                dbPrincipal.categoriaDAO().agregar(categoria = entidadCategoria(8, "Brazos"))
                dbPrincipal.categoriaDAO().agregar(categoria = entidadCategoria(12, "Espalda"))
                dbPrincipal.categoriaDAO().agregar(categoria = entidadCategoria(14, "Pantorrillas"))
                dbPrincipal.categoriaDAO().agregar(categoria = entidadCategoria(15, "Cardio"))
                dbPrincipal.categoriaDAO().agregar(categoria = entidadCategoria(11, "Pecho"))
                dbPrincipal.categoriaDAO().agregar(categoria = entidadCategoria(9, "Piernas"))
                dbPrincipal.categoriaDAO().agregar(categoria = entidadCategoria(13, "Hombros"))
            }

            barra.setProgress(60)
            val call=RetrofitFactory.getRetrofit()
            //val salida=call.extraerEjercicios(idLenguaje = 4)
            //Toast.makeText(this@MainActivity,"${salida.results.size}",Toast.LENGTH_SHORT).show()

           try {
               label.setText("Descargando ejercicios...")
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
            barra.setProgress(90)
            try{
                label.setText("Generando rutinas...")
                val usuario=dbPrincipal.usersDAO().extraerTodo()

                if(usuario.isEmpty()){
                    for (n in 1..7){
                        generarRutina(3,Random.nextInt(5,7))
                    }

                }else{
                    var rutinas=dbPrincipal.rutinaDAO().extraerPorEstado(false)

                    if(usuario[0].ultimoDiaConectado!=fecha.dayOfMonth){

                        for(rutina in rutinas){
                            dbPrincipal.rutinaDAO().eliminarPorID(rutina.id)
                            dbPrincipal.ejerciciosRutinaDAO().eliminarPorId(rutina.id)
                        }
                        for (n in 1..7){
                            generarRutina(3,Random.nextInt(5,7))
                        }
                        val usuario_new=entidadUsuarios(
                                                id = usuario[0].id,
                                                sexo = usuario[0].sexo,
                                                diaNacimiento = usuario[0].diaNacimiento,
                                                mesNacimiento = usuario[0].mesNacimiento,
                                                anioNacimiento = usuario[0].anioNacimiento,
                                                unidadMasa = usuario[0].unidadMasa,
                                                unidadDistancia = usuario[0].unidadDistancia,
                                                unidadMedidaCorporal = usuario[0].unidadMedidaCorporal,
                                                ultimoDiaConectado = fecha.dayOfMonth
                                            )
                        println("Dia: ${usuario[0].diaNacimiento}")
                        println("Mes: ${usuario[0].mesNacimiento}")
                        println("Año: ${usuario[0].anioNacimiento}")

                        dbPrincipal.usersDAO().actualizar(usuario_new)
                    }else{
                        while (rutinas.size<=7){
                            generarRutina(3,Random.nextInt(5,7))
                            rutinas=dbPrincipal.rutinaDAO().extraerPorEstado(false)
                        }
                    }
                }
                label.setText("Iniciando...")
            }catch(ex:Exception){
                println("Error rutinas sistema ${ex.message}")
            }

//---------------------------------------
            barra.setProgress(100)
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
        //Fin de agregar datos iniciales



    }
    suspend fun generarRutina(maxCategorias:Int,maxEjercicios:Int){
        val dbPrincipal= Room.databaseBuilder(this@MainActivity, dbPrincipal::class.java,"user_data").build()
        val categorias=dbPrincipal.categoriaDAO().extraerTodo().shuffled()

        val lista_a= mutableListOf<Int>()

        var nombre=""
        var temp=""

        for(n in 1..Random.nextInt(1, maxCategorias)){
            lista_a.add(categorias[n-1].id)
            nombre+=temp+categorias[n-1].nombre
            temp=" - "
        }

        var ejercicios=dbPrincipal.ejerciciosDAO().extraerPorCategorias(lista_a).shuffled().toMutableList()

        while(ejercicios.size>maxEjercicios){
            ejercicios.removeAt(0)
        }

        dbPrincipal.rutinaDAO().agregar(rutina = entidadRutina(nombre = nombre, segDesc = 60, hechoPorUsuario = false))

        val id=dbPrincipal.rutinaDAO().ultimoID()

        for (ejercicio in ejercicios){
            dbPrincipal.ejerciciosRutinaDAO().agregar(ejercicios = entidadRutinaEjercicios(id_rutina = id, id_ejercicio = ejercicio.id))
        }


    }
}
