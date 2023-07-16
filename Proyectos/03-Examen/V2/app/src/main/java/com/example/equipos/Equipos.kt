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
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat

class Equipos: AppCompatActivity() {
    private lateinit var adaptador: ArrayAdapter<Equipo>
    val arregloEquipos = BaseEquipos.arregloEquipos
    var equipoSeleccionado = 0

    val callbackcontenidoCIntentExplicito = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data != null){
                val data = result.data
                val formatoFecha = SimpleDateFormat("yyyy-MM-dd")
                val nombreModificado = data?.getStringExtra("nombreModificado")
                val paisModificado = data?.getStringExtra("paisModificado")
                val fechaDeFundacionModificado = data?.getStringExtra("fechaDeFundacionModificado")
                val fechaModificada = formatoFecha.parse(fechaDeFundacionModificado)

                //Actualizar la información del Equipo en arregloEquipos List
                val equipo = arregloEquipos[equipoSeleccionado]
                equipo.nombreEquipo = nombreModificado.toString().trim()
                equipo.pais = paisModificado.toString().trim()
                equipo.fechaDeFundacion = fechaModificada.toString()
                BaseEquipos.arregloEquipos[equipoSeleccionado] = equipo
                adaptador.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipos)

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
        //Botones
        //AgregarEquipo
        val botonAgregarEquipo = findViewById<Button>(R.id.btnAgregarE)
        botonAgregarEquipo.setOnClickListener{
            irActividad(ingreso_equipo::class.java)
        }
        //Regresar MenuEquipo
        val botonRegresarME = findViewById<ImageButton>(R.id.btnBackE)
        botonRegresarME.setOnClickListener{
            irActividad(MainActivity::class.java)
        }
        //Regresar HomeEquipo
        val botonRegresarHE = findViewById<ImageButton>(R.id.imbHomeE)
        botonRegresarHE.setOnClickListener{
            irActividad(MainActivity::class.java)
        }


    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        //obtener el id del Arraylist seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        equipoSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.miEditarE ->{
                "${equipoSeleccionado}"
                abrirActividadConParametros(editar_equipo::class.java,equipoSeleccionado)
                return true
            }
            R.id.miBorrarE ->{
                "${equipoSeleccionado}"
                abrirDialogo(equipoSeleccionado)
                return true
            }
            R.id.misJugadores ->{
                "${equipoSeleccionado}"
                abrirActividadConParametros(Jugadores::class.java,equipoSeleccionado)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }

    }

    fun abrirActividadConParametros(
        clase: Class<*>, equipoSeleccionado:Int
    ){
        val intentExplito= Intent(this,clase)
        intentExplito.putExtra("id",equipoSeleccionado)

        callbackcontenidoCIntentExplicito.launch(intentExplito)

    }

    fun abrirDialogo(equipoIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Está seguro de Eliminar?")
        builder.setPositiveButton("Aceptar") { dialog, which ->
            BaseEquipos.arregloEquipos.removeAt(equipoIndex)
            adaptador.notifyDataSetChanged()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancelar", null)

        val dialog = builder.create()
        dialog.show()
    }


}