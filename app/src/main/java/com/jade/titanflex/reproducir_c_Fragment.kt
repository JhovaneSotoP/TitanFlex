package com.jade.titanflex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.jade.titanflex.baseDatos.dbPrincipal
import kotlinx.coroutines.launch
import android.os.SystemClock
import java.util.logging.Handler

class reproducir_c_Fragment : Fragment() {
    lateinit var continuar:Button
    var tiempoDescanso:Int=0
    var tiempoTranscurrido:Int=0

    private lateinit var timerTextView: TextView
    private var startTime = 0L
    private var handler = android.os.Handler()
    private lateinit var updateTimerRunnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_reproducir_c_, container, false)

        val idRutina=(activity as reproducirRutinaActivity).id_rutina



        if (idRutina!=0){
            lifecycleScope.launch{
                val dbPrincipal= Room.databaseBuilder(requireContext(), dbPrincipal::class.java,"user_data").build()
                tiempoDescanso=dbPrincipal.rutinaDAO().extraerPorID(idRutina)[0].segDesc
            }
        }

        continuar=view.findViewById(R.id.btContinuarReproducir_C)
        continuar.setOnClickListener {
            (activity as reproducirRutinaActivity).supportFragmentManager.commit {
                replace<reproducir_a_Fragment>(R.id.contenedorFrameReproducirRutina)
                setReorderingAllowed(true)
            }
        }

        timerTextView = view.findViewById(R.id.btTiempoReproducirC)

        updateTimerRunnable = object : Runnable {
            override fun run() {
                val elapsedMillis = SystemClock.uptimeMillis() - startTime
                val seconds = (elapsedMillis / 1000).toInt()
                val minutes = seconds / 60
                val displaySeconds = seconds % 60
                tiempoTranscurrido=tiempoDescanso-seconds
                if(tiempoTranscurrido>=0){
                    timerTextView.text = String.format("%02d:%02d", tiempoTranscurrido/60, (tiempoDescanso-seconds)%60)
                }
                handler.postDelayed(this, 1000)
            }
        }

        startTimer()

        return view
    }

    private fun startTimer() {
        startTime = SystemClock.uptimeMillis()
        handler.post(updateTimerRunnable)
    }
    private fun stopTimer() {
        handler.removeCallbacks(updateTimerRunnable)
    }
}