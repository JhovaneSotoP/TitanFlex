package com.jade.titanflex

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.jade.titanflex.baseDatos.dbPrincipal
import com.jade.titanflex.rv.itemMesRVAdapter
import com.jade.titanflex.rv.itemMesRVViewModel
import com.jade.titanflex.rv.itemRutina
import com.jade.titanflex.rv.itemRutinasRVAdapter
import com.jade.titanflex.rv.itemRutinasRVViewModel
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [rutinasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class rutinasFragment : Fragment(),listenerRutina{
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

    lateinit var crearRutina: Button
    lateinit var rutinaVacia: Button
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: itemRutinasRVAdapter
    private val itemViewModel: itemRutinasRVViewModel by viewModels()
    //@SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_rutinas, container, false)
        val dbPrincipal= Room.databaseBuilder(requireContext(), dbPrincipal::class.java,"user_data").build()

        recyclerView=view.findViewById(R.id.rvRutinas)
        adapter= itemRutinasRVAdapter(itemViewModel.elementos,this,requireContext())
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(context)



        lifecycleScope.launch {
            val rutinas=dbPrincipal.rutinaDAO().extraerPorEstado(true)
            itemViewModel.elementos.clear()
            for (n in 1..rutinas.size){
                val num=dbPrincipal.ejerciciosRutinaDAO().numeroRegistros(rutinas[n-1].id)
                val item=itemRutina(rutinas[n-1].nombre,num,rutinas[n-1].id, listOf<Int>(0),listOf<Int>(0))
                itemViewModel.elementos.add(item)

            }
            adapter.notifyDataSetChanged()
        }


        crearRutina=view.findViewById(R.id.btCrearRutina)
        crearRutina.setOnClickListener {

            val vista= Intent(context,crearRutinaActivity::class.java)
            startActivity(vista)
        }

        rutinaVacia=view.findViewById(R.id.btRutinaVacia)
        rutinaVacia.setOnClickListener {
            val intent=Intent(context,reproducirRutinaActivity::class.java)
            startActivity(intent)
        }

        return view
    }




    override fun reproducirRutina(id: Int) {
        val intent=Intent(requireContext(),reproducirRutinaActivity::class.java)
        intent.putExtra("id_rutina",id)
        startActivity(intent)
    }

    override fun eliminarRutina(id: Int, pos: Int, contexto: Context) {
        lifecycleScope.launch {
            val dbPrincipal= Room.databaseBuilder(contexto, dbPrincipal::class.java,"user_data").build()
            dbPrincipal.ejerciciosRutinaDAO().eliminarPorId(id)
            dbPrincipal.rutinaDAO().eliminarPorID(id)
            itemViewModel.elementos.removeAt(pos)
            adapter.notifyDataSetChanged()
            Toast.makeText(contexto, "Rutina eliminada", Toast.LENGTH_SHORT).show()
        }
    }


}