package com.jade.titanflex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import kotlin.Any as Any1

class ActividadSecundaria : AppCompatActivity() {
    lateinit var nombreVentana:TextView
    lateinit var btRegresar: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_secundaria)

        nombreVentana=findViewById(R.id.tituloVentanaSecundaria)
        btRegresar=findViewById(R.id.btRegresar)
        btRegresar.setOnClickListener {
            onBackPressed()
        }


        val intent=intent
        if (intent!=null){
            val titulo=intent.getStringExtra("titulo")
            nombreVentana.text = titulo
            val clase=intent.getSerializableExtra("vista") as Class<*>
            val fragmentManager=supportFragmentManager
            val fragmentTransaction=fragmentManager.beginTransaction()
            try {
                val fragment=clase.newInstance() as Fragment
                fragmentTransaction.replace(R.id.contenedorFrameSecundario, fragment)
                fragmentTransaction.commit()
            }catch (ex:Exception){
                Toast.makeText(this,"${ex.message}",Toast.LENGTH_LONG).show()
            }
        }
    }
}