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

class editar_jugador: AppCompatActivity() {
    var arregloJugadores : ArrayList<Jugador>?=null
    var equipoSeleccionado=0
    var jugadorSeleccionado=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_jugador)

        //Recibir los Edit Texts
        val nombreTxt = findViewById<EditText>(R.id.etENombreJ)
        val edadTxt = findViewById<EditText>(R.id.etEEdadJ)
        val dorsalTxt = findViewById<EditText>(R.id.etEDorsalJ)
        val fechaTxt = findViewById<EditText>(R.id.etEFechaNacimientoJ)
        val estaturaTxt = findViewById<EditText>(R.id.etEEstaturaJ)
        val posicionTxt = findViewById<EditText>(R.id.etEPosicionJ)
        val convocadoTxt = findViewById<EditText>(R.id.etEConvocadoJ)

        //Recibir Parámetro
        equipoSeleccionado = intent.getIntExtra("equipo", 1)
        jugadorSeleccionado = intent.getIntExtra("jugador", 1)
        val equipo = BaseEquipos.arregloEquipos.find { it.id == equipoSeleccionado + 1 }

        //arregloJugadores = equipo?.jugadores?: arrayListOf()
        var nextId = arregloJugadores?.size?.plus(1) ?: 1
        //val jugador = arregloJugadores?.get(jugadorSeleccionado)

        /*
        var nombre: String?,
        var edad: Int?,
        var dorsal: Int?,
        var fechaNacimiento: String?,
        var estatura: Double?,
        var posicion: String?,
        var convocado: Boolean
        * */

        consultarJugador(jugadorSeleccionado) { jugador ->
            println("Equipo: " + equipoSeleccionado)
            println("Jugador: " + jugadorSeleccionado)
            //val nombre
            val nombreInicial = jugador?.nombre.toString()
            nombreTxt.setText(nombreInicial)
            //val Edad
            val edadInicial = jugador?.edad.toString()
            edadTxt.setText(edadInicial)
            //val Dorsal
            val dorsalInicioal = jugador?.dorsal.toString()
            dorsalTxt.setText(dorsalInicioal)
            //val FechaNacimiento
            val fechaInicial = jugador?.fechaNacimiento.toString()
            fechaTxt.setText(fechaInicial)
            //val Estatura
            val estaturaInicial = jugador?.estatura.toString()
            estaturaTxt.setText(estaturaInicial)
            //val Posición
            val posicionInicial = jugador?.posicion.toString()
            posicionTxt.setText(posicionInicial)
            //val Convocado
            val convocadoInicial = jugador?.convocado.toString()
            convocadoTxt.setText(convocadoInicial)
        }

        val botonRegresarERH = findViewById<ImageButton>(R.id.imbERegresarRH)
        botonRegresarERH
            .setOnClickListener {
                irActividad(Jugadores::class.java)
            }

        val botonEditarJ = findViewById<Button>(R.id.btnEditarJ)
        botonEditarJ.setOnClickListener {
            val nombre = nombreTxt.text.toString()
            val edad = edadTxt.text.toString()
            val fechaNacimiento = fechaTxt.text.toString()
            val estatura = estaturaTxt.text.toString()
            val posicion = posicionTxt.text.toString()
            val convocado = convocadoTxt.text.toString()
            val foranea = equipoSeleccionado.toString()


            val intentDevolverParametros = Intent()
            intentDevolverParametros.putExtra("nombreModificado", nombre)
            /*intentDevolverParametros.putExtra("edadModificado", edad.toInt())
            intentDevolverParametros.putExtra("fechaModificado", fechaNacimiento.toString())
            intentDevolverParametros.putExtra("estaturaModificado", estatura.toDouble())
            intentDevolverParametros.putExtra("posicionModificado", posicion)
            intentDevolverParametros.putExtra("convocadoModificado", convocado.toBoolean())*/
            val db = Firebase.firestore
            val jugadores = db.collection("jugadores")
            val data = hashMapOf(
                "nombre" to nombre,
                "edad" to edad,
                "fechaNacimiento" to fechaNacimiento,
                "estatura" to estatura,
                "posicion" to posicion,
                "convocado" to convocado,
                "foranea" to foranea
            )
            jugadores.document("${jugadorSeleccionado}").set(data)
            setResult(
                RESULT_OK,
                intentDevolverParametros
            )
            finish()

        }
    }
        fun irActividad(
            clase: Class<*>
        ){
            val intent= Intent(this,clase)
            startActivity(intent)
        }

        fun limpiarArreglo(){
            BaseDatos.jugadoresFire.clear()
        }

        fun consultarJugador(valor:Int, callback:(Jugador) -> Unit){
            val db = Firebase.firestore
            val jugadorRefUnico = db.collection("jugadores")
            var jugadorEncontrado = Jugador(0,"",0,0,"",0.0,"",false,0)
            jugadorRefUnico
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for(document in querySnapshot){
                        val igual = document.id.toInt()
                        println("Igual: "+igual+", Valor: "+valor)
                        if(igual==valor){
                            println("Funcionaaa!!!!")
                            jugadorEncontrado = Jugador(
                                document.id.toInt(),
                                document.data?.get("nombre") as String?,
                                (document.data?.get("edad") as String?)?.toInt(),
                                (document.data?.get("dorsal") as String?)?.toInt(),
                                document.data?.get("fechaNacimiento") as String?,
                                (document.data?.get("estatura") as String?)?.toDouble(),
                                document.data?.get("posicion") as String?,
                                (document.data?.get("convocado") as String?).toBoolean(),
                                (document.data?.get("foranea") as String?)?.toInt()
                            )
                            callback(jugadorEncontrado)
                            return@addOnSuccessListener
                        }
                    }
                    callback(jugadorEncontrado)
                }
                .addOnFailureListener {  }
        }



}