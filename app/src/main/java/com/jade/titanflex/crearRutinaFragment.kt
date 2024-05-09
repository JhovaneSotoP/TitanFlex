package com.jade.titanflex

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jade.titanflex.rv.itemMesRVAdapter
import com.jade.titanflex.rv.itemMesRVViewModel
import com.jade.titanflex.rv.itemMultimediaRVViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [crearRutinaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class crearRutinaFragment : Fragment(),OnItemClickListener {
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



    lateinit var agregarEjercicio: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_crear_rutina, container, false)

        recycler=view.findViewById(R.id.rvCrearRutina)
        (activity as crearRutinaActivity).adapter= itemMesRVAdapter((activity as crearRutinaActivity).itemViewModel.elementos,this)
        recycler.adapter=(activity as crearRutinaActivity).adapter

        recycler.layoutManager= LinearLayoutManager(context)

        agregarEjercicio=view.findViewById(R.id.btAnadirEjercicio)
        agregarEjercicio.setOnClickListener {
            //val vista= Intent(context,ActividadSecundaria::class.java)
            //vista.putExtra("vista",anadirEjercicioFragment::class.java)
            //vista.putExtra("titulo","Buscar Ejercicio")
            //startActivity(vista)
            (activity as crearRutinaActivity).nombreVentana.text="Selecciona un ejercicio"
            activity?.supportFragmentManager?.commit {
                replace<anadirEjercicioFragment>(R.id.contenedorFrameCrearRutina)
                setReorderingAllowed(true)
                addToBackStack("crearRutina")
            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment crearRutinaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            crearRutinaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(position: Int) {
        val datos=(activity as crearRutinaActivity).itemViewModel.elementos
        var pos:Int=0
        for (n in 1..datos.size){
            if (datos[n-1].id==position){
                pos=n-1
                break
            }
        }
        (activity as crearRutinaActivity).itemViewModel.elementos.removeAt(pos)
        (activity as crearRutinaActivity).adapter.notifyDataSetChanged()
        Toast.makeText(context,"Ejercicio eliminado",Toast.LENGTH_SHORT).show()
    }
}