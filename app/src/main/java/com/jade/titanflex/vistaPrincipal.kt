package com.jade.titanflex

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jade.titanflex.baseDatos.dbPrincipal
import kotlinx.coroutines.launch
import java.time.LocalDate


class vistaPrincipal : AppCompatActivity() {
    var cadena: String ="Inicio"
    lateinit var ajustes: ImageView
    lateinit var titulo:TextView



    @RequiresApi(Build.VERSION_CODES.O)
    val fecha= LocalDate.now()


    private lateinit var navigator:BottomNavigationView
    private val mOnNavMenu=BottomNavigationView.OnNavigationItemSelectedListener{ item ->
        when(item.itemId){
            R.id.inicioFragment->{
                cadena="Inicio"
                titulo.setText(cadena)
                supportFragmentManager.commit {
                    replace<InicioFragment>(R.id.contenedorFrame)
                    setReorderingAllowed(true)
                    //addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.rutinasFragment->{
                cadena="Rutinas"
                titulo.setText(cadena)
                supportFragmentManager.commit {
                    replace<rutinasFragment>(R.id.contenedorFrame)
                    setReorderingAllowed(true)
                    //addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.estadisticasFragment->{
                cadena="Estadisticas"
                titulo.setText(cadena)
                supportFragmentManager.commit {
                    replace<estadisticasFragment>(R.id.contenedorFrame)
                    setReorderingAllowed(true)
                    //addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
        }

    false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_principal)



        titulo=findViewById(R.id.tituloVentana)
        ajustes=findViewById(R.id.btAjustes)
        ajustes.setOnClickListener {
            val vista= Intent(this,ActividadSecundaria::class.java)
            vista.putExtra("vista",ajustesFragment::class.java)
            vista.putExtra("titulo","Ajustes")
            startActivity(vista)
        }

        navigator=findViewById(R.id.menuPrincipal)
        navigator.setOnNavigationItemSelectedListener(mOnNavMenu)

        titulo.setText(cadena)
        supportFragmentManager.commit {
            replace<InicioFragment>(R.id.contenedorFrame)
            setReorderingAllowed(true)
            //addToBackStack("replacement")
        }
    }


}
