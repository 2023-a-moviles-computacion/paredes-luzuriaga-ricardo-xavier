package com.example.equipos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date

class ingreso_equipo: AppCompatActivity(){
    private val arreglo = BaseEquipos.arregloEquipos
    private var nextId = arreglo.size+1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingreso_equipo)

        val botonRegresarCV = findViewById<ImageButton>(R.id.imbBackCV)
        botonRegresarCV.setOnClickListener{
            irActividad(Equipos::class.java)
        }
        val botonIngresarE = findViewById<Button>(R.id.btnGuardarE)
        botonIngresarE.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.etNombreE)
            val pais = findViewById<EditText>(R.id.etPaisE)
            val fecha = findViewById<EditText>(R.id.etFechaFundacionE)
            val fechaString  = fecha.text.toString()
            // Create a SimpleDateFormat to parse the date input
            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            val fechaFundacion: Date = try {
                dateFormat.parse(fechaString)
            } catch (e: Exception) {
                Date()
            }
            val jugadores = ArrayList<Jugador>()
            val equipo = Equipo(nextId,nombre.text.toString(), pais.text.toString(),fechaFundacion.toString(),jugadores)
            BaseEquipos.arregloEquipos.add(equipo)
            nextId++
            irActividad(Equipos::class.java)
        }
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }

    }