package com.jade.titanflex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.jade.titanflex.baseDatos.dbPrincipal
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbPrincipal= Room.databaseBuilder(this@MainActivity, dbPrincipal::class.java,"user_data").build()
        val boton=findViewById<Button>(R.id.botonInicio)

        boton.setOnClickListener{
            lifecycleScope.launch{
                try {
                    val salida=dbPrincipal.usersDAO().extraerTodo()
                    var vista= Intent(this@MainActivity,formularioInicial::class.java)
                    if(salida.size>0){
                        Intent(this@MainActivity, vistaPrincipal::class.java).also { vista = it }
                    }
                    startActivity(vista)
                    finish()
                }catch (ex:Exception){
                    println("${ex.message}")
                }
            }

        }
    }
}
