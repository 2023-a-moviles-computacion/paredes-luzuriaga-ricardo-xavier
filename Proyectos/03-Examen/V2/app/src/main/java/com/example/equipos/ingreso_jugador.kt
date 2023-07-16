package com.example.equipos

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date

class ingreso_jugador: AppCompatActivity() {
    var arregloJugadores: ArrayList<Jugador>?=null
    var equipoSeleccionado = 0
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingreso_jugador)

        //Recibir parámetros
        equipoSeleccionado = intent.getIntExtra("id",0)
        val equipo = BaseEquipos.arregloEquipos.find { it.id == equipoSeleccionado+1 }
        arregloJugadores = equipo?.jugadores ?: arrayListOf()
        var nextId = arregloJugadores?.size?.plus(1) ?:1

        val botonRegresarVr = findViewById<ImageButton>(R.id.imbRegresarV)
        botonRegresarVr.setOnClickListener {
            irActividad(Jugadores::class.java)
        }

        val botonAnadirR = findViewById<Button>(R.id.btnGuardarJ)
        botonAnadirR
            .setOnClickListener{
                val nombre = findViewById<EditText>(R.id.etNombreJ)
                val edad = findViewById<EditText>(R.id.etEdadJ)
                val estatura = findViewById<EditText>(R.id.etEstaturaJ)
                val inputFecha = findViewById<EditText>(R.id.etFechaNacimientoJ)
                val fechaString = inputFecha.text.toString()

                // Create a SimpleDateFormat to parse the date input
                val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                val fechaIntegracion: Date = try {
                    dateFormat.parse(fechaString)
                } catch (e: Exception) {
                    Date()
                }
                val check= findViewById<RadioButton>(R.id.rbtSi)
                val convocado = if(check.isChecked){
                    true
                }else{
                    false
                }

                val dorsal = findViewById<EditText>(R.id.etDorsalJ)
                val posicion = findViewById<EditText>(R.id.etPosicionJ)
/*
class Jugador (
    val id:Int,
    var nombre: String?,
    var edad: Int?,
    var dorsal: Int?,
    var fechaNacimiento: String?,
    var estatura: Double?,
    var posicion: String?,
    var convocado: Boolean
)
* */
                val jugador = Jugador(nextId,nombre.text.toString(),edad.text.toString().toInt(), dorsal.text.toString().toInt(), fechaString.toString(), estatura.text.toString().toDouble(), posicion.text.toString(), convocado)
                BaseEquipos.arregloEquipos[equipoSeleccionado].jugadores?.add(jugador)
                nextId  ++

                irActividad(Jugadores::class.java)
            }

    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }
}