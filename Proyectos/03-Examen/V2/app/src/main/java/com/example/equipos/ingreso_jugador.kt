package com.example.equipos

import android.annotation.SuppressLint
import android.content.ComponentCallbacks
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
        equipoSeleccionado = intent.getIntExtra("id",1)
        val equipo = BaseEquipos.arregloEquipos.find { it.id == equipoSeleccionado+1 }

        //arregloJugadores = equipo?.jugadores ?: arrayListOf()
        //var nextId = arregloJugadores?.size?.plus(1) ?:1

        val botonRegresarVr = findViewById<ImageButton>(R.id.imbRegresarV)
        botonRegresarVr.setOnClickListener {
            irActividad(Jugadores::class.java)
        }

        val botonAnadirR = findViewById<Button>(R.id.btnGuardarJ)
        botonAnadirR
            .setOnClickListener{
                val idEquipo = equipoSeleccionado+1
                val foranea = idEquipo.toString()
                val nombre = findViewById<EditText>(R.id.etNombreJ).text.toString()
                val edad = findViewById<EditText>(R.id.etEdadJ).text.toString()
                val estatura = findViewById<EditText>(R.id.etEstaturaJ).text.toString()
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
                    "true"
                }else{
                    "false"
                }
                val dorsal = findViewById<EditText>(R.id.etDorsalJ).text.toString()
                val posicion = findViewById<EditText>(R.id.etPosicionJ).text.toString()
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
* */            val db = Firebase.firestore
                val jugadores = db.collection("jugadores")
                val data = hashMapOf(
                    "nombre" to nombre,
                    "edad" to edad,
                    "dorsal" to dorsal,
                    "fechaNacimiento" to fechaString,
                    "estatura" to estatura,
                    "posicion" to posicion,
                    "convocado" to convocado
                )
                consultarJugador(equipoSeleccionado){
                    conteo -> jugadores.document("${conteo+1}").set(data)
                    val intent = Intent(this, Jugadores::class.java)
                    intent.putExtra("id", idEquipo)
                    startActivity(intent)
                }


                /*val jugador = Jugador(nextId,nombre.text.toString(),edad.text.toString().toInt(), dorsal.text.toString().toInt(), fechaString.toString(), estatura.text.toString().toDouble(), posicion.text.toString(), convocado)
                BaseEquipos.arregloEquipos[equipoSeleccionado].jugadores?.add(jugador)
                nextId  ++
*/
                irActividad(Jugadores::class.java)
            }

    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }

    fun consultarJugador(equipoSeleccionado: Int, callback: (Int) -> Unit){
        var conteo = 0
        val db = Firebase.firestore
        val jugadoresRefUnico = db.collection("jugadores")
        jugadoresRefUnico.get().addOnSuccessListener { querySnapshot ->
            for(document in querySnapshot){
                val equipoxd = (document.data?.get("foranea") as String?)?.toInt()
                conteo++
            }
            callback(conteo)
        }
            .addOnFailureListener {
                exception ->
                println("Error getting documents: $exception")
                callback(-1)
            }
    }
}