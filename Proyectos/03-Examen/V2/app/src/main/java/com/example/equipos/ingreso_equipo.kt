package com.example.equipos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date

class ingreso_equipo: AppCompatActivity(){
    private val arreglo = BaseEquipos.arregloEquipos
    private var nextId = arreglo.size+1
    private var id =  BaseDatos.equiposFire.size+1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingreso_equipo)

        val botonRegresarCV = findViewById<ImageButton>(R.id.imbBackCV)
        botonRegresarCV.setOnClickListener{
            irActividad(Equipos::class.java)
        }
        val botonIngresarE = findViewById<Button>(R.id.btnGuardarE)
        botonIngresarE.setOnClickListener {
            val nombre1 = findViewById<EditText>(R.id.etNombreE)
            val pais1 = findViewById<EditText>(R.id.etPaisE)
            val fecha = findViewById<EditText>(R.id.etFechaFundacionE)

            val nombre = nombre1.text.toString()
            val pais = pais1.text.toString()
            val fechaFundacion  = fecha.text.toString()

            val db = Firebase.firestore
            val equipos = db.collection("equipos")
            val data = hashMapOf(
                "nombre" to nombre,
                "pais" to pais,
                "fechaFundacion" to fechaFundacion
            )
            equipos.document("${id}").set(data)
            println("Esta aquí ${id}")
            irActividad(Equipos::class.java)
        }
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }

    fun limpiarArreglo(){
        BaseDatos.equiposFire.clear()
    }

    }