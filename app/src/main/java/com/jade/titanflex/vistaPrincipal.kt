package com.jade.titanflex

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView


class vistaPrincipal : AppCompatActivity() {
    private lateinit var navigator:BottomNavigationView
    private val mOnNavMenu=BottomNavigationView.OnNavigationItemSelectedListener{ item ->
        when(item.itemId){
            R.id.inicioFragment->{
                supportFragmentManager.commit {
                    replace<InicioFragment>(R.id.contenedorFrame)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.rutinasFragment->{
                supportFragmentManager.commit {
                    replace<rutinasFragment>(R.id.contenedorFrame)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.estadisticasFragment->{
                supportFragmentManager.commit {
                    replace<estadisticasFragment>(R.id.contenedorFrame)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
        }
    false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_principal)

        navigator=findViewById(R.id.menuPrincipal)
        navigator.setOnNavigationItemSelectedListener(mOnNavMenu)

        supportFragmentManager.commit {
            replace<InicioFragment>(R.id.contenedorFrame)
            setReorderingAllowed(true)
            //addToBackStack("replacement")
        }
    }
}