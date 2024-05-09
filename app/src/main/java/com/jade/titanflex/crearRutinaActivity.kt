package com.jade.titanflex

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.jade.titanflex.rv.itemMesRVAdapter
import com.jade.titanflex.rv.itemMesRVViewModel

class crearRutinaActivity : AppCompatActivity() {
    lateinit var btRegresar: ImageView
    lateinit var nombreVentana: TextView
    val itemViewModel: itemMesRVViewModel by viewModels()
    lateinit var adapter: itemMesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_rutina)
        nombreVentana=findViewById(R.id.tituloVentanaCrearRutina)
        nombreVentana.text="Crear rutina"
        btRegresar=findViewById(R.id.btRegresarCrearRutina)
        btRegresar.setOnClickListener {
            onBackPressed()
        }

        supportFragmentManager.commit {
            replace<crearRutinaFragment>(R.id.contenedorFrameCrearRutina)
            setReorderingAllowed(true)
            //addToBackStack("replacement")
        }
    }
}