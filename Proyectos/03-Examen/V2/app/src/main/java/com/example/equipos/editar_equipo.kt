package com.example.equipos

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class editar_equipo: AppCompatActivity() {
    val arregloEquipos = BaseEquipos.arregloEquipos
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_equipo)

        //Recibir parámetros

        val nombreTxt = findViewById<EditText>(R.id.etENombre)
        val paisTxt = findViewById<EditText>(R.id.etEPais)
        val fechaTxt = findViewById<EditText>(R.id.etEFecha)

        val id = intent.getIntExtra("id", 0)
        val equipo = arregloEquipos.get(id)

        consultarEquipo(id) { equipo ->
            println("Equipo Encontrado: ${equipo}")
            //Nombre
            val nombreInicial = equipo.nombreEquipo.toString()
            nombreTxt.setText(nombreInicial)
            //Pais
            val paisInicial = equipo.pais.toString()
            paisTxt.setText(paisInicial)
            //Fecha
            val fechaInicial = equipo.fechaDeFundacion.toString()
            fechaTxt.setText(fechaInicial)

        }


        val botonRegresarECH = findViewById<ImageButton>(R.id.imbERegresar)
        botonRegresarECH
            .setOnClickListener {
                irActividad(Equipos::class.java)
            }

        val botonEditarE = findViewById<Button>(R.id.btnEditar)
        botonEditarE.setOnClickListener {
            //ConsultarEquipo()
            val nombre = nombreTxt.text.toString()
            val pais = paisTxt.text.toString()
            val fechaDeFundacion = fechaTxt.text.toString()

            val db = Firebase.firestore
            val equipos = db.collection("equipos")
            val data = hashMapOf(
                "nombre" to nombre,
                "pais" to pais,
                "fechaDeFundacion" to fechaDeFundacion
            )
            equipos.document("${id}").set(data)

            val intentDevolverParametros = Intent()
            intentDevolverParametros.putExtra("nombreModificado",nombre)
            intentDevolverParametros.putExtra("paisModificado",pais)
            intentDevolverParametros.putExtra("fechaModificada",fechaDeFundacion.toString())

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

    fun consultarEquipo(valor: Int, callback: (Equipo) -> Unit){
        val db = Firebase.firestore
        val equiposRefUnico = db.collection("equipos")
        var equipoEncontrado = Equipo (0,"","","",null)

        equiposRefUnico.get().addOnSuccessListener {
            querySnapshot->
            for(document in querySnapshot){
                val igual = document.id.toInt()
                println("Igual: "+igual+", Valor: "+valor)
                if(igual==valor){
                    println("Si funciona, sí entra")
                    equipoEncontrado = Equipo(
                        document.id.toInt(),
                        document.data?.get("nombre") as String?,
                        document.data?.get("pais") as String?,
                        document.data?.get("fechaDeFundacion") as String?,
                        null
                    )
                    callback(equipoEncontrado)
                    return@addOnSuccessListener
                }
            }
            callback(equipoEncontrado)
        }
            .addOnFailureListener {  }
    }

    fun limpiarArreglo(){
        BaseDatos.equiposFire.clear()
    }

    fun consultarEquipos(){
        val db = Firebase.firestore
        val equiposRefUnico = db.collection("equipos")
        limpiarArreglo()
        equiposRefUnico
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot){
                    BaseDatos.equiposFire.add(
                        Equipo(
                            document.id.toInt(),
                            document.data?.get("nombre") as String?,
                            document.data?.get("pais") as String?,
                            document.data?.get("fechaDeFundacion") as String?,
                            null
                        )
                    )
                }
            }
            .addOnFailureListener {  }
    }


}