package com.jade.titanflex

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.jade.titanflex.baseDatos.dbPrincipal
import com.jade.titanflex.baseDatos.entidadEjerciciosEntrenamiento
import com.jade.titanflex.rv.itemMesRVViewModel
import com.jade.titanflex.rv.itemMultimediaRV
import com.jade.titanflex.rv.itemMultimediaRVAdapter
import com.jade.titanflex.rv.itemMultimediaRVViewModel
import com.jade.titanflex.rv.itemSerie
import com.jade.titanflex.rv.itemSerieRVAdapter
import com.jade.titanflex.rv.itemSerieRVViewModel
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [reproducir_b_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class reproducir_b_Fragment : Fragment(),listenerSerie {
    // TODO: Rename and change types of parameters
    private var id: Int? = null
    lateinit var titulo:TextView
    lateinit var agregarSerie: Button
    lateinit var omitir:Button
    lateinit var continuar:Button

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: itemSerieRVAdapter
    private val itemViewModel: itemSerieRVViewModel by viewModels()

    lateinit var recycler_2: RecyclerView
    lateinit var adapter_2:itemMultimediaRVAdapter
    private val itemVM: itemMultimediaRVViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener("key",this, FragmentResultListener { _, result ->
            id=result.getInt("id")
            //Toast.makeText(context,"$id",Toast.LENGTH_SHORT).show()
            actualizarDatos()
        })
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_reproducir_b_, container, false)

        recycler_2=view.findViewById(R.id.rvMultimediaReproducirB)
        adapter_2= itemMultimediaRVAdapter(itemVM.elementos,requireContext())
        recycler_2.adapter=adapter_2
        recycler_2.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        recyclerView=view.findViewById(R.id.rvReproducirB)
        adapter= itemSerieRVAdapter(itemViewModel.elementos,this)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(requireContext())

        titulo=view.findViewById(R.id.tvNombreReproducirB)

        agregarSerie=view.findViewById(R.id.btAgregarSerie)
        agregarSerie.setOnClickListener {
            itemViewModel.elementos.add(itemSerie(0,0.0f))
            adapter.notifyDataSetChanged()
            estadoContinuar()
        }

        omitir=view.findViewById(R.id.btOmitirReproducir_B)
        omitir.setOnClickListener {
            iniciarDescanso()
        }

        continuar=view.findViewById(R.id.btContinuarReproducir_B)
        continuar.setOnClickListener {
            for(n in 1..itemViewModel.elementos.size){
                (activity as reproducirRutinaActivity).listaSeries.add(
                    entidadEjerciciosEntrenamiento(id_entrenamiento = 0, id_ejercicio = id!!, repeticiones = itemViewModel.elementos[n-1].rep, peso = itemViewModel.elementos[n-1].pes)
                )
            }
            iniciarDescanso()
        }
        estadoContinuar()
        return view
    }
    fun estadoContinuar(){
        if(itemViewModel.elementos.size>0){
            continuar.isEnabled=true
        }else{
            continuar.isEnabled=false
        }
    }
    fun iniciarDescanso(){
        (activity as reproducirRutinaActivity).supportFragmentManager.commit {
            replace<reproducir_c_Fragment>(R.id.contenedorFrameReproducirRutina)
            setReorderingAllowed(true)
        }
    }

    fun actualizarDatos(){
        lifecycleScope.launch{
            val dbPrincipal= Room.databaseBuilder(requireContext(), dbPrincipal::class.java,"user_data").build()
            if(id!=null){
                val id_=id!!
                val ejercicio=dbPrincipal.ejerciciosDAO().extraerPorID(id_)

                try{
                    titulo.setText(ejercicio[0].name)
                }catch (ex:Exception){
                    println("${ex.message}")
                }
                try{
                    val multimedia=dbPrincipal.multimediaDAO().extraerSegunID(id_)
                    if(multimedia.size==0){
                        itemVM.elementos.add(itemMultimediaRV("",true))
                    }
                    for(n in 1..multimedia.size){
                        itemVM.elementos.add(itemMultimediaRV(multimedia[n-1].url,multimedia[n-1].isImage))
                    }
                    adapter_2.notifyDataSetChanged()
                }catch (ex:Exception){
                    println("${ex.message}")
                }
            }
        }
    }


    override fun actualizarRep(repe: Int, pos: Int) {
        itemViewModel.elementos[pos].rep=repe

    }

    override fun actualizarPes(peso: Float, pos: Int) {
        itemViewModel.elementos[pos].pes=peso
    }

    override fun eliminarItem(pos: Int) {
        try{
            itemViewModel.elementos.removeAt(pos)
            adapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Serie eliminada", Toast.LENGTH_SHORT).show()
        }catch (ex:Exception){
            println("${ex.message}")
        }
    }
}