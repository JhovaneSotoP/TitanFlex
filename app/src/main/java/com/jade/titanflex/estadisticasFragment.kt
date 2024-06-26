package com.jade.titanflex

import android.app.AlertDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.text.parseAsHtml
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.jade.titanflex.baseDatos.dbPrincipal
import com.jade.titanflex.baseDatos.entidadMedidas
import com.jade.titanflex.rv.itemHistorial
import com.jade.titanflex.rv.itemHistorialRVAdapter
import com.jade.titanflex.rv.itemHistorialRVViewModel
import com.jade.titanflex.rv.itemMultimediaRVAdapter
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [estadisticasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class estadisticasFragment : Fragment(),listenerHistorial {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var lineChart: LineChart
    private lateinit var lineChart_1: LineChart
    private lateinit var lineChart_2: LineChart
    private lateinit var lineChart_3: LineChart
    private lateinit var lineChart_4: LineChart
    private lateinit var agregarPeso: Button
    private lateinit var imc:TextView

    private lateinit var racha: TextView
    private lateinit var rachaMax:TextView

    lateinit var recyclerView: RecyclerView
    lateinit var adapter:itemHistorialRVAdapter
    private val ivm:itemHistorialRVViewModel by viewModels()

    private val pesos= mutableListOf<elementoGrafica>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_estadisticas, container, false)

        lineChart = view.findViewById(R.id.lineChart)
        lineChart_1 = view.findViewById(R.id.lineChart_1)
        lineChart_2 = view.findViewById(R.id.lineChart_2)
        lineChart_3 = view.findViewById(R.id.lineChart_3)
        lineChart_4 = view.findViewById(R.id.lineChart_4)

        imc=view.findViewById(R.id.imc)

        agregarPeso=view.findViewById(R.id.btAgregarPeso)
        agregarPeso.setOnClickListener { mostrarDialogo() }

        racha=view.findViewById(R.id.rachaEstadisticas)
        rachaMax=view.findViewById(R.id.rachaMaximaEstadisticas)

        racha.setText((activity as vistaPrincipal).racha.toString())
        rachaMax.setText((activity as vistaPrincipal).maxRacha.toString())


        recyclerView=view.findViewById(R.id.rvHistorial)
        adapter=itemHistorialRVAdapter(ivm.elementos,this)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(requireContext())


        actualizarPesos()
        actualizarGraficas()
        actualizarIMC()
        actualizarHistorial()


        return view
    }

    private fun actualizarHistorial() {
        lifecycleScope.launch {
            try{
                val dbPrincipal= Room.databaseBuilder(requireContext(), dbPrincipal::class.java,"user_data").build()
                val entrenamientos=dbPrincipal.enntrenamientoDAO().extraerTodoInvertido()
                ivm.elementos.clear()
                for(entrenamiento in entrenamientos){

                    val ejercicios=dbPrincipal.seriesDAO().extraerPorEntrenamiento(entrenamiento.id)
                    var texto=""

                    var nombre_e=""
                    var series_e=1
                    var rep_e=0
                    var vol_e=0.0f
                    var ejerc=0

                    var temp=ejercicios[0].id_ejercicio
                    var temp_2=-1

                     for(ejercicio in ejercicios){
                         val nombre=dbPrincipal.ejerciciosDAO().extraerPorID(ejercicio.id_ejercicio)[0].name

                         if(temp_2!=ejercicio.id_ejercicio){
                             ejerc+=1
                             temp_2=ejercicio.id_ejercicio
                         }

                         if(temp==ejercicio.id_ejercicio){
                             nombre_e=nombre
                             series_e+=1
                             rep_e+=ejercicio.repeticiones
                             vol_e+=ejercicio.peso
                         }else{
                             texto+="<li><b>$nombre_e</b> <br>$series_e series<br>$rep_e reps.<br>$vol_e kgs.</li><br>"
                             nombre_e=nombre
                             series_e=1
                             rep_e=ejercicio.repeticiones
                             vol_e=ejercicio.peso
                         }
                     }
                    texto+="<li><b>$nombre_e</b> <br>$series_e series<br>$rep_e reps.<br>$vol_e kgs.</li><br>"
                    texto="<ul>$texto</ul>"
                    println(texto)

                    val fecha="${entrenamiento.dia} - ${entrenamiento.mes} - ${entrenamiento.anio}"
                    ivm.elementos.add(itemHistorial(fecha,entrenamiento.duracion,ejerc,texto))
                }
                adapter.notifyDataSetChanged()
            }catch (ex:Exception){
                println("${ex.message}")
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun actualizarIMC() {
        lifecycleScope.launch {
            val dbPrincipal= Room.databaseBuilder(requireContext(), dbPrincipal::class.java,"user_data").build()
            try{
                val usuario=dbPrincipal.usersDAO().extraerTodo()[0]

                val temp_1=dbPrincipal.medidasDAO().extraerMaxIDMedida(1)
                val peso=dbPrincipal.medidasDAO().extraerPeso(temp_1,1)[0].valor

                val temp_2=dbPrincipal.medidasDAO().extraerMaxIDMedida(5)
                val altura=dbPrincipal.medidasDAO().extraerPeso(temp_2,5)[0].valor

                println("Dia: ${usuario.diaNacimiento}")
                println("Mes: ${usuario.mesNacimiento}")
                println("Año: ${usuario.anioNacimiento}")

                val imc_g= calcularIMCyRangoPorEdad(peso,altura,usuario.sexo,usuario.diaNacimiento,usuario.mesNacimiento,usuario.anioNacimiento)

                imc.setText("${imc_g.imc} (${imc_g.rango})")

            }catch(ex:Exception){
                println("El error es: ${ex.message}")
            }
        }
    }

    fun actualizarGraficas(){
        lifecycleScope.launch {
            generarGrafica(lineChart,(activity as vistaPrincipal).ejerciciosMes,"Ejercicios en el ultimo mes")
            generarGrafica(lineChart_1,(activity as vistaPrincipal).repeticionesMes,"Repeticiones en el ultimo mes")
            generarGrafica(lineChart_2,(activity as vistaPrincipal).volumenMes,"Volumen en el ultimo mes")
            generarGrafica(lineChart_3,(activity as vistaPrincipal).tiempoMes,"Tiempo en el ultimo mes")
            generarGrafica(lineChart_4,pesos,"Peso del usuario")
        }
    }
    suspend fun generarGrafica(graf:LineChart,data:List<elementoGrafica>,etiqueta:String){
        // Configuración del gráfico
        graf.description.isEnabled = false
        graf.setDrawGridBackground(false)

        val legend = graf.legend
        legend.form = Legend.LegendForm.LINE

        // Datos de ejemplo
        val entries = ArrayList<Entry>()
        for (dato in data){
            entries.add(Entry(dato.x,dato.y))

        }

        val dataSet = LineDataSet(entries, etiqueta)
        dataSet.color = resources.getColor(R.color.purple_500)
        dataSet.setDrawValues(false)
        dataSet.setDrawCircles(false)

        val lineData = LineData(dataSet)
        graf.data = lineData
        graf.invalidate() // Refresca el gráfico
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun actualizarPesos(){
        lifecycleScope.launch{
            val dbPrincipal= Room.databaseBuilder(requireContext(), dbPrincipal::class.java,"user_data").build()
            val med=dbPrincipal.medidasDAO().extraerTodosPesos()
            for(n in 1..med.size){
                pesos.add(elementoGrafica(n.toFloat(), med[n-1].valor))
            }

        }
        actualizarIMC()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun mostrarDialogo() {
        val editText = EditText(requireContext())
        editText.inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Ingresar tu peso actual (kgs)")
            .setView(editText)
            .setPositiveButton("Aceptar") { dialog, which ->
                val inputText = editText.text.toString()
                try {
                    val number = inputText.toFloat()
                    // Hacer algo con el número flotante
                    guardarPeso(number)
                    Toast.makeText(requireContext(), "Número ingresado: $number", Toast.LENGTH_SHORT).show()
                } catch (e: NumberFormatException) {
                    Toast.makeText(requireContext(), "Por favor ingresa un número válido", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun guardarPeso(peso:Float){
        lifecycleScope.launch{
            val dbPrincipal= Room.databaseBuilder(requireContext(), dbPrincipal::class.java,"user_data").build()
            val fecha=(activity as vistaPrincipal).fecha
            val existe=dbPrincipal.medidasDAO().extraerPesoFecha(fecha.dayOfMonth,fecha.monthValue,fecha.year)
            if (existe.size>0){
                dbPrincipal.medidasDAO().eliminar(existe[0])
            }

            dbPrincipal.medidasDAO().agregar(entidadMedidas(id_medida = 1, valor = peso, dia = fecha.dayOfMonth,mes=fecha.monthValue, anio = fecha.year))
        }
        actualizarPesos()
        actualizarGraficas()
    }

    override fun mostrarEjercicios(textHTML: String) {
        val htmlText =textHTML

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Ejercicios Realizados")

        val view = layoutInflater.inflate(R.layout.dialog_html, null)
        val textView = view.findViewById<TextView>(R.id.textViewDialog)
        textView.text = htmlText.parseAsHtml()

        builder.setView(view)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }
}