package com.jade.titanflex

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.jade.titanflex.baseDatos.dbPrincipal
import com.jade.titanflex.baseDatos.entidadRutina
import com.jade.titanflex.rv.itemMesRVAdapter
import com.jade.titanflex.rv.itemRutinaSistemaRVAdapter
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [InicioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InicioFragment : Fragment(),listenerRutina {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var contenedorSemanal:CardView

    var num_ejercicios=0
    var num_repeticiones=0
    var num_volumen=0.0f

    lateinit var  ejerciciosHoy:TextView
    lateinit var  repeticionesHoy:TextView
    lateinit var  volumenHoy:TextView

    lateinit var domingoTV:TextView
    lateinit var lunesTV:TextView
    lateinit var martesTV:TextView
    lateinit var miercolesTV:TextView
    lateinit var juevesTV:TextView
    lateinit var viernesTV:TextView
    lateinit var sabadoTV:TextView

    lateinit var domingoCV:CardView
    lateinit var lunesCV:CardView
    lateinit var martesCV:CardView
    lateinit var miercolesCV:CardView
    lateinit var juevesCV:CardView
    lateinit var viernesCV:CardView
    lateinit var sabadoCV:CardView

    lateinit var recycler: RecyclerView
    lateinit var adapter: itemRutinaSistemaRVAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_inicio, container, false)

        contenedorSemanal=view.findViewById(R.id.contenedor_semanal)
        contenedorSemanal.setOnClickListener {
            val vista= Intent(context,ActividadSecundaria::class.java)
            vista.putExtra("vista",objetivoSemanalFragment::class.java)
            vista.putExtra("titulo","Objetivos Semanales")
            startActivity(vista)
        }

        ejerciciosHoy=view.findViewById(R.id.ejerciciosHoy)
        volumenHoy=view.findViewById(R.id.volumenHoy)
        repeticionesHoy=view.findViewById(R.id.RepeticionesHoy)

        domingoTV=view.findViewById(R.id.domingoDia)
        lunesTV=view.findViewById(R.id.lunesDia)
        martesTV=view.findViewById(R.id.martesDia)
        miercolesTV=view.findViewById(R.id.miercolesDia)
        juevesTV=view.findViewById(R.id.juevesDia)
        viernesTV=view.findViewById(R.id.viernesDia)
        sabadoTV=view.findViewById(R.id.sabadoDia)

        domingoCV=view.findViewById(R.id.domingoCV)
        lunesCV=view.findViewById(R.id.lunesCV)
        martesCV=view.findViewById(R.id.martesCV)
        miercolesCV=view.findViewById(R.id.miercolesCV)
        juevesCV=view.findViewById(R.id.juevesCV)
        viernesCV=view.findViewById(R.id.viernesCV)
        sabadoCV=view.findViewById(R.id.sabadoCV)

        recycler=view.findViewById(R.id.rvRutinasRecomendadas)
        adapter=itemRutinaSistemaRVAdapter((activity as vistaPrincipal).itemViewModel.elementos,this,requireContext())
        recycler.adapter=adapter
        recycler.layoutManager=LinearLayoutManager(requireContext())

        (activity as vistaPrincipal).actualizarRutinasRecomendadas(adapter)


        actualizarInformacionHoy()
        actualizarContenedor()

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun actualizarInformacionHoy(){
        lifecycleScope.launch {
            val dbPrincipal= Room.databaseBuilder(requireContext(), dbPrincipal::class.java,"user_data").build()
            val entrenamientos=dbPrincipal.enntrenamientoDAO().extraerPorFecha((activity as vistaPrincipal).fecha.dayOfMonth,(activity as vistaPrincipal).fecha.monthValue,(activity as vistaPrincipal).fecha.year)
            for(entrenamiento in entrenamientos){
                var num_temp=-1
                val series=dbPrincipal.seriesDAO().extraerPorEntrenamiento(entrenamiento.id)
                for(serie in series){
                    num_repeticiones+=serie.repeticiones
                    num_volumen+=serie.peso
                    if(serie.id_ejercicio!=num_temp){
                        num_ejercicios+=1
                        num_temp=serie.id_ejercicio
                    }
                }
            }
            ejerciciosHoy.setText(num_ejercicios.toString())
            repeticionesHoy.setText(num_repeticiones.toString())
            volumenHoy.setText(num_volumen.toString())
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.O)
    fun actualizarContenedor(){
        lifecycleScope.launch {

            val fecha=(activity as vistaPrincipal).fecha
            var fechaIniciar=fecha
            var diaSemana=fecha.dayOfWeek.toString()

            while(diaSemana!="SUNDAY"){
                println(diaSemana.toString())
                fechaIniciar=fechaIniciar.minusDays(1)
                diaSemana=fechaIniciar.dayOfWeek.toString()
            }

            val dbPrincipal= Room.databaseBuilder(requireContext(), dbPrincipal::class.java,"user_data").build()

            val listaTV= mutableListOf<TextView>()
            listaTV.add(domingoTV)
            listaTV.add(lunesTV)
            listaTV.add(martesTV)
            listaTV.add(miercolesTV)
            listaTV.add(juevesTV)
            listaTV.add(viernesTV)
            listaTV.add(sabadoTV)

            val listaCV= mutableListOf<CardView>()
            listaCV.add(domingoCV)
            listaCV.add(lunesCV)
            listaCV.add(martesCV)
            listaCV.add(miercolesCV)
            listaCV.add(juevesCV)
            listaCV.add(viernesCV)
            listaCV.add(sabadoCV)

            val dao=dbPrincipal.enntrenamientoDAO()

            for(n in 1..listaTV.size){
                listaTV[n-1].setText(fechaIniciar.dayOfMonth.toString())

                if(fechaIniciar.dayOfMonth==fecha.dayOfMonth){
                    listaCV[n-1].background=requireContext().getDrawable(R.drawable.fondo_card_semanal)
                }

                val entrenamiento=dao.extraerPorFecha(fechaIniciar.dayOfMonth,fechaIniciar.monthValue,fechaIniciar.year)

                if (entrenamiento.size>0){
                    listaCV[n-1].setCardBackgroundColor(requireContext().getColor(R.color.azul_b))
                }

                fechaIniciar=fechaIniciar.plusDays(1)

            }
        }




    }

    override fun reproducirRutina(id: Int) {
        val intent=Intent(requireContext(),reproducirRutinaActivity::class.java)
        intent.putExtra("id_rutina",id)
        startActivity(intent)
    }

    override fun eliminarRutina(id: Int, pos: Int, contexto: Context) {
        lifecycleScope.launch {
            Toast.makeText(contexto, "Migrando rutina...", Toast.LENGTH_SHORT).show()

            val dbPrincipal= Room.databaseBuilder(contexto, dbPrincipal::class.java,"user_data").build()


            try {
                val rutina=dbPrincipal.rutinaDAO().extraerPorID(id)[0]
                val rutina_nuevo=entidadRutina(
                    id = rutina.id,
                    nombre = rutina.nombre,
                    segDesc = rutina.segDesc,
                    hechoPorUsuario = true
                )
                dbPrincipal.rutinaDAO().actualizar(rutina_nuevo)
            }catch (ex:Exception){}

            (activity as vistaPrincipal).itemViewModel.elementos.removeAt(pos)
            adapter.notifyDataSetChanged()
            Toast.makeText(contexto, "Rutina migrada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun agregarManiqui(
        context: Context,
        image_a: ImageView,
        image_b: ImageView,
        prin: List<elementosMusculo>,
        sec: List<elementosMusculo>,
    ) {

    }


}