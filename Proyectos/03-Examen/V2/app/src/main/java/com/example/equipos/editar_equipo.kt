package com.example.equipos

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat

class editar_equipo: AppCompatActivity() {
    val arregloEquipos = BaseEquipos.arregloEquipos
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_equipo)

        //Recibir parámetros
        val id = intent.getIntExtra("id",0)
        val equipo = arregloEquipos.get(id)

        //Nombre
        val nombreTxt = findViewById<EditText>(R.id.etENombre)
        val nombreInicial = equipo.nombreEquipo.toString()
        nombreTxt.setText(nombreInicial)
        //Pais
        val paisTxt = findViewById<EditText>(R.id.etEPais)
        val paisInicial = equipo.pais.toString()
        paisTxt.setText(paisInicial)
        //Fecha
        val fechaTxt = findViewById<EditText>(R.id.etEFecha)
        val fechaInicial = equipo.fechaDeFundacion.toString()
        fechaTxt.setText(fechaInicial)

        val botonRegresarECH = findViewById<ImageButton>(R.id.imbERegresar)
        botonRegresarECH
            .setOnClickListener {
                irActividad(Equipos::class.java)
            }

        val botonEditarE = findViewById<Button>(R.id.btnEditar)
        botonEditarE.setOnClickListener {
            val nombre = nombreTxt.text.toString()
            val pais = paisTxt.text.toString()
            val fechaDeFundacion = fechaTxt.text.toString()

            val nuevoEquipo = BaseEquipos.arregloEquipos[id]
            nuevoEquipo.nombreEquipo=nombre
            nuevoEquipo.pais=pais
            val formatoFecha = SimpleDateFormat("dd-MM-yyyy")
            val fechaNueva = formatoFecha.parse(fechaDeFundacion)
            nuevoEquipo.fechaDeFundacion=fechaNueva.toString()

            val intentDevolverParametros = Intent()
            intentDevolverParametros.putExtra("nombreModificado",nombre)
            intentDevolverParametros.putExtra("paisModificado",pais)
            intentDevolverParametros.putExtra("fechaModificada",fechaNueva.toString())
        }
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }
}