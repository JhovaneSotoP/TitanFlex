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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [anadirEjercicioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class anadirEjercicioFragment : Fragment(),OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    lateinit var recycler: RecyclerView
    lateinit var adapter: itemMesRVAdapter
    private val itemViewModel:itemMesRVViewModel by viewModels()
    lateinit var barraBusqueda:SearchView
    lateinit var cantResultados:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_anadir_ejercicio, container, false)


        cantResultados=view.findViewById(R.id.tvCantResultadosEjercicio)
        barraBusqueda=view.findViewById(R.id.barraBusquedaEjercicios)
        barraBusqueda.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=null){
                    actualizardatos(newText)
                }else{
                    actualizardatos("")
                }
                return true
            }


        })

        recycler=view.findViewById(R.id.rvMostrarEjercicios)
        adapter= itemMesRVAdapter(itemViewModel.elementos,this,requireContext())
        recycler.adapter=adapter

        recycler.layoutManager= LinearLayoutManager(context)
        return view
    }

    private fun actualizardatos(cadena:String){
        lifecycleScope.launch {
            itemViewModel.elementos.clear()
            val dbPrincipal=
                context?.let { Room.databaseBuilder(it, dbPrincipal::class.java,"user_data").build() }
            itemViewModel.elementos.clear()
            val salida=dbPrincipal?.ejerciciosDAO()?.extraerPorNombre(cadena)
            if (salida!=null){
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
                cantResultados.text="${salida.size} resultados"
                adapter.notifyDataSetChanged()


            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment anadirEjercicioFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            anadirEjercicioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(position: Int) {
        itemViewModel.elementos.clear()
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