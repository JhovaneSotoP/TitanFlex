package com.jade.titanflex

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.htmlEncode
import androidx.core.text.parseAsHtml
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.commit
import androidx.fragment.app.replace
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
    private lateinit var musculos_a:TextView
    private lateinit var musculos_b:TextView
    private lateinit var imagen_a: ImageView
    private lateinit var imagen_b: ImageView


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

        if(activity is crearRutinaActivity){
            (activity as crearRutinaActivity).nombreVentana.text=""
        }
        recycler=view.findViewById(R.id.rvMultimediaEjercicios)
        adapter= itemMultimediaRVAdapter(itemViewModel.elementos,requireContext())
        recycler.adapter=adapter
        recycler.layoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        nombre=view.findViewById(R.id.tvNombreDetalleEjercicio)
        categoria=view.findViewById(R.id.tvCategoriaDetalleEjercicio)
        detalle=view.findViewById(R.id.tvdescripcionDetalleEjercicio)
        equipo=view.findViewById(R.id.tvEquipoDetalleEjercicio)
        musculos_a=view.findViewById(R.id.tvDetallesMusculos_a)
        musculos_b=view.findViewById(R.id.tvDetallesMusculos_b)

        imagen_a=view.findViewById(R.id.ivImagenMusculoDetalleEjercicio)
        imagen_b=view.findViewById(R.id.ivImagenMusculoDetalleEjercicio_b)

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
            if(activity is crearRutinaActivity){
                (activity as crearRutinaActivity).nombreVentana.text="Crear Rutina"
            }

        }

        botonAceptar.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val backStackCount = fragmentManager.backStackEntryCount
            if (backStackCount >= 2) {
                val antepenultimoFragmentId = fragmentManager.getBackStackEntryAt(backStackCount - 2).id
                fragmentManager.popBackStack(antepenultimoFragmentId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            } else {

            }
            if(activity is crearRutinaActivity){
                (activity as crearRutinaActivity).nombreVentana.text="Crear Rutina"
                (activity as crearRutinaActivity).itemViewModel.elementos.add(itemMesRV(image_,nombre_,categoria_,id_))
                (activity as crearRutinaActivity).adapter.notifyDataSetChanged()
            }
            if(activity is reproducirRutinaActivity){
                (activity as reproducirRutinaActivity).itemViewModel.elementos.add(itemMesRV(image_,nombre_,categoria_,id_))
                (activity as reproducirRutinaActivity).supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                activity?.supportFragmentManager?.commit {
                    replace<reproducir_a_Fragment>(R.id.contenedorFrameReproducirRutina)
                    setReorderingAllowed(true)
                    //addToBackStack("replacement")
                }

            }
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
                        val equipos=dbPrincipal.equipoEjerciciosDAO().extraerPorID(id_)
                        equipo.text=dbPrincipal.equipoDAO().extraerPorID(equipos[0].num)[0].nombre
                    }catch (ex:Exception){
                        println("")
                    }

                    try {
                        val principales=dbPrincipal.musculosEjerciciosDAO().extraerPrimarioPorID(id_)
                        var lista=""
                        val lista_1= mutableListOf<elementosMusculo>()
                        val lista_2= mutableListOf<elementosMusculo>()
                        for(n in 1..principales.size){
                            val item=dbPrincipal.musculoDAO().extraerPorID(principales[n-1].num)
                            if(item[0].nombre_en!=""){
                                lista+="<li>${item[0].nombre} (${item[0].nombre_en})</li>"
                            }else{
                                lista+="<li>${item[0].nombre}</li>"
                            }

                            lista_1.add(elementosMusculo(item[0].imageMain,item[0].is_front))
                        }
                        musculos_a.text="<ul>${lista}</ul>".parseAsHtml()

                        val secundarios=dbPrincipal.musculosEjerciciosDAO().extraerSecundarioPorID(id_)
                        lista=""
                        for(n in 1..secundarios.size){
                            val item=dbPrincipal.musculoDAO().extraerPorID(secundarios[n-1].num)
                            if(item[0].nombre_en!=""){
                                lista+="<li>${item[0].nombre} (${item[0].nombre_en})</li>"
                            }else{
                                lista+="<li>${item[0].nombre}</li>"
                            }
                            lista_2.add(elementosMusculo(item[0].imageSecondary,item[0].is_front))
                        }
                        musculos_b.text="<ul>${lista}</ul>".parseAsHtml()

                        crearManiqui(requireContext(),imagen_a,imagen_b,lista_1,lista_2)

                    }catch (ex:Exception){
                       println("")
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