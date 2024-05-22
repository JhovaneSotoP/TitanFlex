package com.jade.titanflex

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
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
class estadisticasFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var lineChart: LineChart
    private lateinit var lineChart_1: LineChart
    private lateinit var lineChart_2: LineChart
    private lateinit var lineChart_3: LineChart


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



        actualizarGraficas()


        return view
    }

    fun actualizarGraficas(){
        lifecycleScope.launch {
            generarGrafica(lineChart,(activity as vistaPrincipal).ejerciciosMes,"Ejercicios en el ultimo mes")
            generarGrafica(lineChart_1,(activity as vistaPrincipal).repeticionesMes,"Repeticiones en el ultimo mes")
            generarGrafica(lineChart_2,(activity as vistaPrincipal).volumenMes,"Volumen en el ultimo mes")
            generarGrafica(lineChart_3,(activity as vistaPrincipal).tiempoMes,"Tiempo en el ultimo mes")

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

}