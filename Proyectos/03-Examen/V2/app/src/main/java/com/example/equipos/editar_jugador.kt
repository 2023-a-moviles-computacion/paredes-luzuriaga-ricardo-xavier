package com.example.equipos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat

class editar_jugador: AppCompatActivity() {
    var arregloJugadores : ArrayList<Jugador>?=null
    var equipoSeleccionado=0
    var jugadorSeleccionado=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_jugador)

        //Recibir Parámetro
        equipoSeleccionado = intent.getIntExtra("equipo",0)
        jugadorSeleccionado = intent.getIntExtra("jugador",0)
        val equipo = BaseEquipos.arregloEquipos.find { it.id  == equipoSeleccionado+1 }
        arregloJugadores = equipo?.jugadores?: arrayListOf()
        var nextId = arregloJugadores?.size?.plus(1) ?:1
        val jugador = arregloJugadores?.get(jugadorSeleccionado)

        /*
        var nombre: String?,
        var edad: Int?,
        var dorsal: Int?,
        var fechaNacimiento: String?,
        var estatura: Double?,
        var posicion: String?,
        var convocado: Boolean
        * */

        //val nombre
        val nombreTxt = findViewById<EditText>(R.id.etENombreJ)
        val nombreInicial = jugador?.nombre.toString()
        nombreTxt.setText(nombreInicial)
        //val Edad
        val edadTxt = findViewById<EditText>(R.id.etEEdadJ)
        val edadInicial = jugador?.edad.toString()
        edadTxt.setText(edadInicial)
        //val Dorsal
        val dorsalTxt = findViewById<EditText>(R.id.etEDorsalJ)
        val dorsalInicioal = jugador?.dorsal.toString()
        dorsalTxt.setText(dorsalInicioal)
        //val FechaNacimiento
        val fechaTxt = findViewById<EditText>(R.id.etEFechaNacimientoJ)
        val fechaInicial = jugador?.fechaNacimiento.toString()
        fechaTxt.setText(fechaInicial)
        //val Estatura
        val estaturaTxt = findViewById<EditText>(R.id.etEEstaturaJ)
        val estaturaInicial = jugador?.estatura.toString()
        estaturaTxt.setText(estaturaInicial)
        //val Posición
        val posicionTxt = findViewById<EditText>(R.id.etEPosicionJ)
        val posicionInicial = jugador?.posicion.toString()
        posicionTxt.setText(posicionInicial)
        //val Convocado
        val convocadoTxt = findViewById<EditText>(R.id.etEConvocadoJ)
        val convocadoInicial = jugador?.convocado.toString()
        convocadoTxt.setText(convocadoInicial)

        val botonEditarJ = findViewById<Button>(R.id.btnEditarJ)
        botonEditarJ.setOnClickListener {
            val nombre = nombreTxt.text.toString()
            val edad = edadTxt.text.toString()
            val fechaNacimiento = fechaTxt.text.toString()
            val estatura = estaturaTxt.text.toString()
            val posicion = posicionTxt.text.toString()
            val convocado = convocadoTxt.text.toString()
            val intentDevolverParametros = Intent()
            intentDevolverParametros.putExtra("nombreModificado", nombre)
            intentDevolverParametros.putExtra("edadModificado", edad.toInt())
            intentDevolverParametros.putExtra("fechaModificado", fechaNacimiento.toString())
            intentDevolverParametros.putExtra("estaturaModificado", estatura.toDouble())
            intentDevolverParametros.putExtra("posicionModificado", posicion)
            intentDevolverParametros.putExtra("convocadoModificado", convocado.toBoolean())

            setResult(
                RESULT_OK,
                intentDevolverParametros
            )
            finish()

        }

        fun irActividad(
            clase: Class<*>
        ){
            val intent= Intent(this,clase)
            startActivity(intent)
        }
    }
}