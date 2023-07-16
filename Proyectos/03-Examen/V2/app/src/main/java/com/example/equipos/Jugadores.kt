package com.example.equipos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat

class Jugadores: AppCompatActivity(){
    var arregloJugadores: ArrayList<Jugador>?=null
    private lateinit var adaptador: ArrayAdapter<Jugador>
    var jugadorSeleccionado = 0
    var equipoSeleccionado = 0

    val callbackContenidoCIntentExplicito = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        /*
        *val id:Int,
    var nombre: String,
    var edad: Int,
    var dorsal: Int,
    var fechaNacimiento: String,
    var estatura: Double,
    var posicion: String,
    var convocado: Boolean
        * */
        result ->
        if(result.resultCode== Activity.RESULT_OK){
            if(result.data !=null) {
                val data=result.data
                val nombreModificado = data?.getStringExtra("nombreModificado")
                val edadModificado = data?.getIntExtra("edadModificado",0)
                val dorsalModificado = data?.getIntExtra("dorsalModificado",0)
                val fechaNacimientoModificado = data?.getStringExtra("fechaNacimientoModificado")
                val estaturaModificado = data?.getDoubleExtra("estaturaModificado",0.0)
                val posicionModificado = data?.getStringExtra("posicionModificado")
                val convocadoModificado = data?.getBooleanExtra("convocadoModificado",false)
                var jugador = Jugador(
                    jugadorSeleccionado+1,
                    nombreModificado,
                    edadModificado,
                    dorsalModificado,
                    fechaNacimientoModificado.toString(),
                    estaturaModificado,
                    posicionModificado,
                    false
                )

                BaseEquipos.arregloEquipos[equipoSeleccionado]?.jugadores?.set(jugadorSeleccionado, jugador)
                adaptador.notifyDataSetChanged()

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jugador)
        //Recibir parámetro
        equipoSeleccionado = intent.getIntExtra("id",0)
        val equipo = BaseEquipos.arregloEquipos.find { it.id == equipoSeleccionado+1 }
        arregloJugadores = equipo?.jugadores?: arrayListOf()

        //Crear el ListView y el adaptador
        val listView = findViewById<ListView>(R.id.lvJugadores)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloJugadores!!
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

        //Crear Jugador
        val botonCrearJugador = findViewById<Button>(R.id.btnAgregarJ)
        botonCrearJugador.setOnClickListener {
            irActividad(ingreso_jugador::class.java)
        }
        //Regresar al Home
        val botonRegresarAC = findViewById<ImageButton>(R.id.imbRegresarRJ)
        botonRegresarAC
            .setOnClickListener {
                devolverRespuesta()
                irActividad(Equipos::class.java)
            }
        //Regresar al home
        val botonRegresarAHome = findViewById<ImageButton>(R.id.ibRegresarHJ)
        botonRegresarAHome
            .setOnClickListener {
                irActividad(MainActivity::class.java)
            }
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }
    fun devolverRespuesta(){
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("nombreModificado","Eden Hazard")
        intentDevolverParametros.putExtra("edadModificado",31)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menujugador,menu)
        //obtener el id del Arraylist seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        jugadorSeleccionado=id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar_jugador ->{
                "${jugadorSeleccionado}"
                abrirActividadConParametros(editar_jugador::class.java,jugadorSeleccionado,equipoSeleccionado)
                return true
            }
            R.id.mi_eliminar_jugador ->{
                "${jugadorSeleccionado}"
                abrirDialogo(equipoSeleccionado,jugadorSeleccionado)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }

    }
    fun abrirDialogo(equipoIndex: Int,jugadorIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton("Aceptar") { dialog, which ->
            BaseEquipos.arregloEquipos[equipoIndex]?.jugadores?.removeAt(jugadorIndex)
            adaptador.notifyDataSetChanged()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancelar", null)

        val dialog = builder.create()
        dialog.show()
    }

    fun abrirActividadConParametros(
        clase: Class<*>, jugadorSeleccionado:Int,equipoSeleccionado:Int
    ){
        val intentExplito= Intent(this,clase)
        intentExplito.putExtra("Equipo",equipoSeleccionado)
        intentExplito.putExtra("Jugador",jugadorSeleccionado)

        callbackContenidoCIntentExplicito.launch(intentExplito)

    }
}