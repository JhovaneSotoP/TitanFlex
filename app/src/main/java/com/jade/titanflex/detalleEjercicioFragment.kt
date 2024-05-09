package com.jade.titanflex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.htmlEncode
import androidx.core.text.parseAsHtml
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.jade.titanflex.baseDatos.dbPrincipal
import com.jade.titanflex.rv.itemMesRV
import com.jade.titanflex.rv.itemMesRVAdapter
import com.jade.titanflex.rv.itemMesRVViewModel
import com.jade.titanflex.rv.itemMultimediaRV
import com.jade.titanflex.rv.itemMultimediaRVAdapter
import com.jade.titanflex.rv.itemMultimediaRVViewModel
import kotlinx.coroutines.launch


class detalleEjercicioFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener("key",this, FragmentResultListener { _, result ->
            id=result.getInt("id")
            //Toast.makeText(context,"$id",Toast.LENGTH_SHORT).show()
            actualizarDatos()
        })

    }

    lateinit var recycler: RecyclerView
    lateinit var adapter: itemMultimediaRVAdapter
    private val itemViewModel: itemMultimediaRVViewModel by viewModels()

    private lateinit var nombre:TextView
    private lateinit var categoria:TextView
    private lateinit var detalle:TextView
    private lateinit var equipo:TextView
    private lateinit var musculos:TextView

    private lateinit var botonAceptar:Button
    private lateinit var botonCancelar:Button

    private var nombre_:String=""
    private var categoria_:String=""
    private var image_:String=""
    private var id_:Int=0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_detalle_ejercicio, container, false)

        (activity as crearRutinaActivity).nombreVentana.text=""
        recycler=view.findViewById(R.id.rvMultimediaEjercicios)
        adapter= itemMultimediaRVAdapter(itemViewModel.elementos)
        recycler.adapter=adapter
        recycler.layoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        nombre=view.findViewById(R.id.tvNombreDetalleEjercicio)
        categoria=view.findViewById(R.id.tvCategoriaDetalleEjercicio)
        detalle=view.findViewById(R.id.tvdescripcionDetalleEjercicio)
        //equipo=view.findViewById(R.id.tvEquipoDetalleEjercicio)
        //musculos=view.findViewById(R.id.tvMusculosDetalleEjercicio)

        botonAceptar=view.findViewById(R.id.btAceptarDetalleEjercicio)
        botonCancelar=view.findViewById(R.id.btCancelarDetalleEjercicio)

        botonCancelar.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val backStackCount = fragmentManager.backStackEntryCount
            if (backStackCount >= 2) {
                val antepenultimoFragmentId = fragmentManager.getBackStackEntryAt(backStackCount - 2).id
                fragmentManager.popBackStack(antepenultimoFragmentId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            } else {
                // No hay suficientes Fragmentos en la pila para regresar al antepenúltimo, puedes manejar esta situación según tus necesidades
            }
            (activity as crearRutinaActivity).nombreVentana.text="Crear Rutina"
        }

        botonAceptar.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val backStackCount = fragmentManager.backStackEntryCount
            if (backStackCount >= 2) {
                val antepenultimoFragmentId = fragmentManager.getBackStackEntryAt(backStackCount - 2).id
                fragmentManager.popBackStack(antepenultimoFragmentId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            } else {
                // No hay suficientes Fragmentos en la pila para regresar al antepenúltimo, puedes manejar esta situación según tus necesidades
            }
            (activity as crearRutinaActivity).nombreVentana.text="Crear Rutina"
            (activity as crearRutinaActivity).itemViewModel.elementos.add(itemMesRV(image_,nombre_,categoria_,id_))
            (activity as crearRutinaActivity).adapter.notifyDataSetChanged()
        }



        return view
    }

    private fun actualizarDatos(){
        val dbPrincipal= Room.databaseBuilder(requireContext(), dbPrincipal::class.java,"user_data").build()

        if(dbPrincipal!=null){
            println("La base de datos esta vacia")
        }

        if(id!=null){
            println("El id esta vacio")
        }

        if(dbPrincipal!=null&&id!=null){
            lifecycleScope.launch {
                val ejerciciosActuales=dbPrincipal.ejerciciosDAO().extraerPorID(id!!)
                if (ejerciciosActuales.isNotEmpty()){
                    id_=id!!
                    val ejercicio=ejerciciosActuales[0]
                    nombre.text=ejercicio.name
                    nombre_=ejercicio.name
                    detalle.text=ejercicio.description.parseAsHtml()
                    try {
                        categoria.text=dbPrincipal.categoriaDAO().extraerSegunID(ejercicio.category)[0].nombre
                        categoria_=categoria.text.toString()
                    }catch (ex:Exception){
                            categoria.text=""
                        }

                    try {
                        val imagenes=dbPrincipal.multimediaDAO().extraerSegunID(ejercicio.id)
                        itemViewModel.elementos.clear()
                        if(imagenes.isEmpty()){
                            itemViewModel.elementos.add(itemMultimediaRV("",true))
                        }
                        for(n in 1..imagenes.size){
                            image_=imagenes[0].url
                            itemViewModel.elementos.add(itemMultimediaRV(imagenes[n-1].url,imagenes[n-1].isImage))
                        }
                        adapter.notifyDataSetChanged()
                    }catch (ex:Exception){
                        println("Error en las imagenes")
                    }

                }
            }
        }
    }



}