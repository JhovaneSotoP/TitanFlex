package com.jade.titanflex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
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
import kotlinx.coroutines.launch



class anadirEjercicioFragment : Fragment(),OnItemClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    lateinit var recycler: RecyclerView
    lateinit var adapter: itemMesRVAdapter
    private val itemViewModel:itemMesRVViewModel by viewModels()
    lateinit var barraBusqueda:SearchView
    lateinit var cantResultados:TextView
    var banderaDuplicado=true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_anadir_ejercicio, container, false)


        cantResultados=view.findViewById(R.id.tvCantResultadosEjercicio)
        barraBusqueda=view.findViewById(R.id.barraBusquedaEjercicios)

        val listenerTexto=object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=null){
                    actualizardatos(newText)
                }else{
                    //actualizardatos("")
                }
                return true
            }


        }
        barraBusqueda.setOnQueryTextListener(listenerTexto)

        listenerTexto.onQueryTextChange("")

        recycler=view.findViewById(R.id.rvMostrarEjercicios)
        adapter= itemMesRVAdapter(itemViewModel.elementos,this,requireContext())
        recycler.adapter=adapter

        recycler.layoutManager= LinearLayoutManager(context)
        return view
    }

    override fun onResume() {
        super.onResume()
        //actualizardatos("")
    }

    private fun actualizardatos(cadena:String){
        lifecycleScope.launch {
            if(banderaDuplicado){
                banderaDuplicado=false
                val dbPrincipal=
                    context?.let { Room.databaseBuilder(it, dbPrincipal::class.java,"user_data").build() }
                val salida=dbPrincipal?.ejerciciosDAO()?.extraerPorNombre(cadena)
                if (salida!=null){
                    itemViewModel.elementos.clear()
                    for(n in 1..salida.size){
                        val categoria=dbPrincipal.categoriaDAO().extraerSegunID(salida[n-1].category)

                        var temp:String=""
                        var desc:String=""
                        for(k in 1..categoria.size){
                            desc += temp
                            desc+=categoria[k-1].nombre
                            temp=", "
                        }

                        var imagen:String=""
                        val imagenes=dbPrincipal.multimediaDAO().extraerSegunID(salida[n-1].id)
                        if(imagenes.isNotEmpty()){
                            imagen=imagenes[0].url
                        }
                        itemViewModel.elementos.add(itemMesRV(imagen,salida[n-1].name,desc,salida[n-1].id))
                    }
                    cantResultados.text="${itemViewModel.elementos.size} resultados"



                }
                adapter.notifyDataSetChanged()
                banderaDuplicado=true
            }
        }
    }



    override fun onItemClick(position: Int) {
        //itemViewModel.elementos.clear()
        //Toast.makeText(context,"$position",Toast.LENGTH_SHORT).show()
        val bundle=Bundle()
        bundle.putInt("id",position)

        parentFragmentManager.setFragmentResult("key",bundle)
        if(activity is crearRutinaActivity){
            (activity as crearRutinaActivity).supportFragmentManager.commit {

                val nuevoFragmento = detalleEjercicioFragment()
                replace(R.id.contenedorFrameCrearRutina, nuevoFragmento)

                replace<detalleEjercicioFragment>(R.id.contenedorFrameCrearRutina)
                setReorderingAllowed(true)
                addToBackStack("replacement")
            }
        }

        if(activity is reproducirRutinaActivity){
            (activity as reproducirRutinaActivity).supportFragmentManager.commit {

                val nuevoFragmento = detalleEjercicioFragment()
                replace(R.id.contenedorFrameReproducirRutina, nuevoFragmento)

                replace<detalleEjercicioFragment>(R.id.contenedorFrameReproducirRutina)
                setReorderingAllowed(true)
                addToBackStack("replacement")
            }
        }
    }
}