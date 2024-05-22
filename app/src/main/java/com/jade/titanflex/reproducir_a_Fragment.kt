package com.jade.titanflex

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.room.Room
import com.jade.titanflex.baseDatos.dbPrincipal
import com.jade.titanflex.baseDatos.entidadEjerciciosEntrenamiento
import com.jade.titanflex.baseDatos.entidadEntrenamiento
import com.jade.titanflex.rv.itemMesRVAdapter
import kotlinx.coroutines.launch
import java.time.LocalDate


class reproducir_a_Fragment : Fragment(),OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    lateinit var adapter: itemMesRVAdapter
    lateinit var recycler: RecyclerView

    lateinit var cerrar: Button
    lateinit var terminar:Button
    lateinit var agregar:Button
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_reproducir_a_, container, false)
        recycler=view.findViewById(R.id.rvReproducirA)
        adapter= itemMesRVAdapter((activity as reproducirRutinaActivity).itemViewModel.elementos,this,requireContext())
        recycler.adapter=adapter
        recycler.layoutManager=LinearLayoutManager(requireContext())
        (activity as reproducirRutinaActivity).actualizarDatos(adapter)
        //Toast.makeText(requireContext(), "${(activity as reproducirRutinaActivity).itemViewModel.elementos.size}", Toast.LENGTH_SHORT).show()

        cerrar=view.findViewById(R.id.btSalirReproducir_A)
        cerrar.setOnClickListener {
            (activity as reproducirRutinaActivity).finish()
        }

        terminar=view.findViewById(R.id.btTerminarReproducir_A)
        terminar.setOnClickListener {
            //Toast.makeText(requireContext(), "${(activity as reproducirRutinaActivity).listaSeries.size}", Toast.LENGTH_SHORT).show()
            guardarDatos()

        }
        if((activity as reproducirRutinaActivity).listaSeries.size>0){
            terminar.isEnabled=true
        }else{
            terminar.isEnabled=false
        }

        agregar=view.findViewById(R.id.btReproducirA_agregar)
        agregar.setOnClickListener {
            (activity as reproducirRutinaActivity).supportFragmentManager.commit {
                replace<anadirEjercicioFragment>(R.id.contenedorFrameReproducirRutina)
                setReorderingAllowed(true)
            }
        }

        return view
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun guardarDatos(){
        lifecycleScope.launch {
            var ultimo_id=0
            try{
                (activity as reproducirRutinaActivity).stopTimer()
                val timer=(activity as reproducirRutinaActivity).tiempoEntrenamiento

                val dbPrincipal= Room.databaseBuilder(requireContext(), dbPrincipal::class.java,"user_data").build()
                val fechaActual= LocalDate.now()
                dbPrincipal.enntrenamientoDAO().agregar(entidadEntrenamiento(duracion = timer,dia=fechaActual.dayOfMonth,mes=fechaActual.monthValue, anio = fechaActual.year))
                ultimo_id=dbPrincipal.enntrenamientoDAO().ultimoID()
                for (n in 1..(activity as reproducirRutinaActivity).listaSeries.size){
                    val temp=(activity as reproducirRutinaActivity).listaSeries[n-1]
                    val item=entidadEjerciciosEntrenamiento(id_entrenamiento = ultimo_id, id_ejercicio = temp.id_ejercicio, pos = n-1, repeticiones = temp.repeticiones, peso = temp.peso, unRep = temp.unRep, unPeso = temp.unPeso)
                    dbPrincipal.seriesDAO().agregar(item)
                }
            }catch(ex:Exception){
                println("El error es:${ex.message}")
            }

            val bundle=Bundle()
            bundle.putInt("id",ultimo_id)
            parentFragmentManager.setFragmentResult("key",bundle)
            (activity as reproducirRutinaActivity).supportFragmentManager.commit {
                replace<reproducir_d_Fragment>(R.id.contenedorFrameReproducirRutina)
            }
        }

    }

    override fun onItemClick(position: Int) {
        val bundle=Bundle()
        bundle.putInt("id",position)
        parentFragmentManager.setFragmentResult("key",bundle)
        activity?.supportFragmentManager?.commit {
            replace<reproducir_b_Fragment>(R.id.contenedorFrameReproducirRutina)
            setReorderingAllowed(true)
            //addToBackStack("replacement")
        }

        for(n in 1..(activity as reproducirRutinaActivity).itemViewModel.elementos.size){
            if ((activity as reproducirRutinaActivity).itemViewModel.elementos[n-1].id==position){
                (activity as reproducirRutinaActivity).itemViewModel.elementos.removeAt(n-1)
                //Toast.makeText(requireContext(), "$position", Toast.LENGTH_SHORT).show()
                break
            }
        }

    }

}