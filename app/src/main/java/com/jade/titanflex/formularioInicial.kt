package com.jade.titanflex

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.jade.titanflex.baseDatos.dbPrincipal
import com.jade.titanflex.baseDatos.entidadMedidas
import com.jade.titanflex.baseDatos.entidadUsuarios
import kotlinx.coroutines.launch
import java.time.LocalDate

class formularioInicial : AppCompatActivity() {
    var bandera:Boolean=false
    var sexo:Int = 0
    var dia:Int = 0
    var mes:Int = 0
    var anio:Int = 0
    var peso:Float = 0.0f
    var altura:Float = 0.0f


    lateinit var cvMale:CardView
    lateinit var cvFemale:CardView
    lateinit var btFecha: Button
    lateinit var etPeso: EditText
    lateinit var etAltura: EditText
    lateinit var btContinuar: Button
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_inicial)

        val dbPrincipal= Room.databaseBuilder(this@formularioInicial, dbPrincipal::class.java,"user_data").build()
        val fechaActual= LocalDate.now()
        cvMale=findViewById(R.id.viewMale)
        cvFemale=findViewById(R.id.viewFemale)
        cambiarValores(1)
        cvMale.setOnClickListener { cambiarValores(1) }
        cvFemale.setOnClickListener { cambiarValores(0) }

        etPeso=findViewById(R.id.pesoFormulario)
        etPeso.addTextChangedListener { botonListo() }
        etAltura=findViewById(R.id.alturaFormulario)
        etAltura.addTextChangedListener{botonListo()}

        btContinuar=findViewById(R.id.btn_Formulario)

        btFecha=findViewById(R.id.fechaFormulario)
        val datePickerDialog=DatePickerDialog(
            this,{view,year,monthOfYear,dayOfMonth->
                 anio=year
                mes=monthOfYear
                dia=dayOfMonth
                 },2024,4,23
        )

        btFecha.setOnClickListener {
            datePickerDialog.show()
            botonListo()}

        btContinuar.setOnClickListener {
            lifecycleScope.launch {
                try {
                    dbPrincipal.usersDAO().agregar(usuario = entidadUsuarios(id=0,sexo= sexo, diaNacimiento = dia, mesNacimiento = mes, anioNacimiento = anio, unidadMasa = 1, unidadDistancia = 3, unidadMedidaCorporal = 5))
                    dbPrincipal.medidasDAO().agregar(medida = entidadMedidas(id_medida = 1, valor = peso, dia = fechaActual.dayOfMonth,mes=fechaActual.monthValue, anio = fechaActual.year))
                    dbPrincipal.medidasDAO().agregar(medida = entidadMedidas(id_medida = 5, valor = altura, dia = fechaActual.dayOfMonth,mes=fechaActual.monthValue, anio = fechaActual.year))

                    println("${fechaActual.dayOfMonth}/${fechaActual.monthValue}/${fechaActual.year}")
                    var vista= Intent(this@formularioInicial,vistaPrincipal::class.java)
                    startActivity(vista)
                    finish()
                }catch (ex:Exception){
                    Toast.makeText(this@formularioInicial,"${ex.message}",Toast.LENGTH_LONG).show()

                }
            }
        }
    }

    private fun botonListo(){
        try {
            peso=etPeso.text.toString().toFloat()
            altura=etAltura.text.toString().toFloat()
        }catch (ex:Exception){
            peso=0.0f
            altura=0.0f
        }
        if(dia>0&&peso>0&&altura>0){
            btContinuar.isEnabled=true
        }else{
            btContinuar.isEnabled=false
        }
    }
    private fun cambiarValores(actual:Int){
        if(actual!=sexo){
            if (sexo==0){
                sexo=1
                cvFemale.setBackgroundColor(ContextCompat.getColor(this,R.color.purpura_a))
                cvMale.setBackgroundColor(ContextCompat.getColor(this,R.color.azul_a))
            }else{
                sexo=0
                cvMale.setBackgroundColor(ContextCompat.getColor(this,R.color.purpura_a))
                cvFemale.setBackgroundColor(ContextCompat.getColor(this,R.color.azul_a))
            }
        }
    }
}