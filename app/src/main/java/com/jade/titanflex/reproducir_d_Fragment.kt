package com.jade.titanflex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.jade.titanflex.baseDatos.dbPrincipal
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 * Use the [reproducir_d_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class reproducir_d_Fragment : Fragment() {
    private var id=0

    lateinit var tiempoTV:TextView
    lateinit var ejerciciosTV:TextView
    lateinit var seriesTV:TextView
    lateinit var repeticionesTV:TextView
    lateinit var volumenTV:TextView

    lateinit var image_a:ImageView
    lateinit var image_b:ImageView

    lateinit var aceptar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener("key",this, FragmentResultListener { _, result ->
            id=result.getInt("id")
            //Toast.makeText(context,"$id",Toast.LENGTH_SHORT).show()
            actualizarDatos()
        })
    }

    private fun actualizarDatos() {
        lifecycleScope.launch {
            var tiempo=0
            var ejercicios=0
            var series=0
            var repeticiones=0
            var volumen=0.0f

            val dbPrincipal= Room.databaseBuilder(requireContext(), dbPrincipal::class.java,"user_data").build()
            val entrenamiento=dbPrincipal.enntrenamientoDAO().extraerPorID(id)
            val lista_1= mutableListOf<elementosMusculo>()
            val lista_2= mutableListOf<elementosMusculo>()
            if(entrenamiento.size>0){
                var temp=0
                tiempo=entrenamiento[0].duracion
                val ejer=dbPrincipal.seriesDAO().extraerPorEntrenamiento(entrenamiento[0].id)
                series=ejer.size
                for(n in 1..ejer.size){
                    repeticiones+=ejer[n-1].repeticiones
                    volumen+=ejer[n-1].peso

                    if(temp!=ejer[n-1].id_ejercicio){
                        ejercicios+=1
                        temp=ejer[n-1].id_ejercicio
                    }
                }

                for (ejercicio in ejer){
                    val musc=dbPrincipal.musculosEjerciciosDAO().extraerPrimarioPorID(ejercicio.id_ejercicio)
                    for (muscu in musc){
                        val prin=dbPrincipal.musculoDAO().extraerPorID(muscu.num)
                        lista_1.add(elementosMusculo(prin[0].imageMain,prin[0].is_front))

                    }

                    val musc_2=dbPrincipal.musculosEjerciciosDAO().extraerSecundarioPorID(ejercicio.id_ejercicio)
                    for (muscu in musc_2){
                        val prin=dbPrincipal.musculoDAO().extraerPorID(muscu.num)
                        lista_2.add(elementosMusculo(prin[0].imageSecondary,prin[0].is_front))

                    }
                }

            }

            tiempoTV.setText(String.format("%02d:%02d minutos", tiempo/60, (tiempo)%60))

            ejerciciosTV.setText("${ejercicios} ejercicios")
            seriesTV.setText("${series} series")
            repeticionesTV.setText("${repeticiones} repeticiones")
            volumenTV.text= "${volumen} kgs."

            crearManiqui(requireContext(),image_a,image_b,lista_1,lista_2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_reproducir_d_, container, false)
        tiempoTV=view.findViewById(R.id.tvTiempoReproducir_D)
        ejerciciosTV=view.findViewById(R.id.tvEjerciciosReproducir_D)
        seriesTV=view.findViewById(R.id.tvSeriesReproducir_D)
        repeticionesTV=view.findViewById(R.id.tvRepeticionesReproducir_D)
        volumenTV=view.findViewById(R.id.tvVolumenReproducir_D)

        image_a=view.findViewById(R.id.ivCuerpoAReproducirD)
        image_b=view.findViewById(R.id.ivCuerpoBReproducirD)

        aceptar=view.findViewById(R.id.btAceptarReproducir_D)
        aceptar.setOnClickListener {
            (activity as reproducirRutinaActivity).finish()
        }
        return view
    }

}