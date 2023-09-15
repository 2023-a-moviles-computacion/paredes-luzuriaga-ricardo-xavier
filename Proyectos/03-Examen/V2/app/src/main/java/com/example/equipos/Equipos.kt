package com.example.equipos

import android.app.Activity
import android.app.AlertDialog
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
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat


class Equipos: AppCompatActivity() {
    private lateinit var adaptador: ArrayAdapter<Equipo>

    var equipoSeleccionado = 0
    var equipoList = BaseDatos.tablaEquipo?.retrieveAllEquipos() ?: emptyList()
    var query : Query? = null
    var list = equipoList.toList()

    val callbackcontenidoCIntentExplicito = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data != null) {
                val data = result.data

                val nombreModificado = data?.getStringExtra("nombreModificado")
                /*val formatoFecha = SimpleDateFormat("yyyy-MM-dd")
                val paisModificado = data?.getStringExtra("paisModificado")
                val fechaDeFundacionModificado = data?.getStringExtra("fechaDeFundacionModificado")
                val fechaModificada = formatoFecha.parse(fechaDeFundacionModificado)

                //Actualizar la información del Equipo en arregloEquipos List
                val equipo = arregloEquipos[equipoSeleccionado]
                equipo.nombreEquipo = nombreModificado.toString().trim()
                equipo.pais = paisModificado.toString().trim()
                equipo.fechaDeFundacion = fechaModificada.toString()
                BaseEquipos.arregloEquipos[equipoSeleccionado] = equipo
                adaptador.notifyDataSetChanged()*/
                consultarEquipo2(adaptador)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipos)
        /*
        //Crear el listView y el adaptador
        val listView = findViewById<ListView>(R.id.lvEquipos)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            BaseEquipos.arregloEquipos
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
         */

        val listView = findViewById<ListView>(R.id.lvEquipos)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            /*BaseDatos.equiposFire*/ BaseEquipos.arregloEquipos
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        consultarEquipo2(adaptador)
        registerForContextMenu(listView)



        //Botones
        //AgregarEquipo
        val botonAgregarEquipo = findViewById<Button>(R.id.btnAgregarE)
        botonAgregarEquipo.setOnClickListener {
            irActividad(ingreso_equipo::class.java)
        }
        //Regresar MenuEquipo
        val botonRegresarME = findViewById<ImageButton>(R.id.btnBackE)
        botonRegresarME.setOnClickListener {
            irActividad(MainActivity::class.java)
        }
        //Regresar HomeEquipo
        val botonRegresarHE = findViewById<ImageButton>(R.id.imbHomeE)
        botonRegresarHE.setOnClickListener {
            irActividad(MainActivity::class.java)
        }


    }
    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }



    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //Llenamos las características del Equipo
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        //obtener el id del Arraylist seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        equipoSeleccionado = id+1
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miEditarE -> {
                "${equipoSeleccionado}"
                abrirActividadConParametros(editar_equipo::class.java, equipoSeleccionado)
                return true
            }

            R.id.miBorrarE -> {
                "${equipoSeleccionado}"
                abrirDialogo(equipoSeleccionado)
                return true
            }

            R.id.misJugadores -> {
                "${equipoSeleccionado}"
                abrirActividadConParametros(Jugadores::class.java, equipoSeleccionado)
                return true
            }

            else -> return super.onContextItemSelected(item)
        }

    }

    fun abrirActividadConParametros(
        clase: Class<*>, equipoSeleccionado: Int
    ) {
        val intentExplito = Intent(this, clase)
        intentExplito.putExtra("id", equipoSeleccionado)

        callbackcontenidoCIntentExplicito.launch(intentExplito)

    }

    fun abrirDialogo(equipoIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Está seguro de Eliminar?")
        builder.setPositiveButton("Aceptar") { dialog, which ->
            val id = equipoIndex
            eliminarRegistro(id)
            //BaseEquipos.arregloEquipos.removeAt(equipoIndex)
            adaptador.notifyDataSetChanged()
            dialog.dismiss()
            irActividad(Equipos::class.java)
        }
        builder.setNegativeButton("Cancelar", null)

        val dialog = builder.create()
        dialog.show()
    }
    fun consultarConOrderBy(
        adaptador: ArrayAdapter<Equipo>
    ){
        val db = Firebase.firestore
        val equiposRefUnico=db.collection("equipos")
        adaptador.notifyDataSetChanged()
        equiposRefUnico
            .orderBy("id",Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                for (equipo in it){
                    equipo.id
                }
            }
            .addOnFailureListener {  }
    }

    fun limpiarArreglo(){
        BaseDatos.equiposFire.clear()
    }

    fun anadirAArregloEquipo(
        equipo: QueryDocumentSnapshot
    ) {
        val nuevoEquipo = Equipo(
            equipo.data.get("id") as Int,
            equipo.data.get("nombreEquipo") as String?,
            equipo.data.get("pais") as String?,
            equipo.data.get("fechaDeFundacion") as String?,
            equipo.data.get("jugadores") as ArrayList<Jugador>?
        )
        BaseDatos.equiposFire.add(nuevoEquipo)
    }

    fun guardarQuery(
        documentSnapshots: QuerySnapshot,
        refEquipos:Query
    ){
        if(documentSnapshots.size() > 0){
            val ultimoDocumento = documentSnapshots
                .documents[documentSnapshots.size() - 1]
            query = refEquipos
                .startAfter(ultimoDocumento)
        }
    }
    fun eliminarRegistro(id:Int){
        var cadena="${id}"
        val db = Firebase.firestore
        val refEquipo= db.collection("equipos")
        refEquipo
            .document(cadena)
            .delete()
            .addOnCompleteListener{  }
            .addOnFailureListener {  }
    }
    fun consultarEquipo2(
        adaptador:ArrayAdapter<Equipo>
    ){
        val db = Firebase.firestore
        val equiposRefUnico = db.collection("equipos")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        equiposRefUnico.get().addOnSuccessListener {
            querySnapshot ->
            for (document in querySnapshot){
                BaseDatos.equiposFire.add(
                    Equipo(
                        document.id.toInt(),
                        document.data?.get("nombreEquipo") as String?,
                        document.data?.get("pais") as String?,
                        document.data?.get("fechaDeFundacion") as String?,
                        document.data?.get("jugadores") as ArrayList<Jugador>?
                    )
                )
            }
            adaptador.notifyDataSetChanged()
        }
            .addOnFailureListener{}

    }

    fun crearDatosPrueba(){
        val db = Firebase.firestore
        val equiposPrueba = db.collection("equipos")
        var jugador1= Jugador(3, "Cristiano Ronaldo", 38, 7, "05-02-1985", 1.87, "Delantero - DC", true,0)
    }




}