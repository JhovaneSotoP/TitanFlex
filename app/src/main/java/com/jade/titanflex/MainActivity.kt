package com.jade.titanflex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.jade.titanflex.DB.Medidas
import com.jade.titanflex.DB.MedidasDB
import com.jade.titanflex.DB.inicioApp
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val DBPrincipal= Room.databaseBuilder(this@MainActivity, MedidasDB::class.java,"user_data").build()
        val boton=findViewById<Button>(R.id.botonInicio)

        boton.setOnClickListener{
            lifecycleScope.launch{
                try {
                    val salida=DBPrincipal.medidasDAO().extraerTodo()
                    var vista= Intent(this@MainActivity,formularioInicial::class.java)
                    if(salida.size>0){
                        Intent(this@MainActivity,inicioApp::class.java).also { vista = it }
                    }
                    startActivity(vista)
                }catch (ex:Exception){
                    println("${ex.message}")
                }
            }

        }
    }
}
