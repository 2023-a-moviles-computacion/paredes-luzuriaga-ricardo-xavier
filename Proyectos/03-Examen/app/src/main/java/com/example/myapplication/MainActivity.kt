package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), AdaptadorListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var room: Database
    private lateinit var adaptadorJugadores: AdaptadorJugadores
    private lateinit var adaptadorEquipos: AdaptadorEquipos
    private var listaJugadores: MutableList<Jugador> = mutableListOf()
    private var listaEquipos: MutableList<Equipo> = mutableListOf()
    private var equipoSeleccionado: Equipo? = null
    private var isModoJugador = true
    private var contadorEquipo: Int = 0
    private var contadorIdEquipo: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //Aquí esta el error y toca corregirlo
        setContentView(binding.root)

        room = Room.databaseBuilder(this, Database::class.java, "Database").build()

        binding.rvEquipos.layoutManager = LinearLayoutManager(this)
        binding.rvJugadores.layoutManager = LinearLayoutManager(this)

        adaptadorEquipos = AdaptadorEquipos(listaEquipos, this)
        binding.rvEquipos.adapter = adaptadorEquipos

        adaptadorJugadores = AdaptadorJugadores(listaJugadores, listaEquipos, this)
        binding.rvJugadores.adapter = adaptadorJugadores

        binding.btnAddUpdate.setOnClickListener {
            if (isModoJugador) {
                agregarOActualizarJugador()
            } else {
                agregarOActualizarEquipo()
            }
        }

        binding.btnCancelarJugador.setOnClickListener {
            limpiarCampos()
        }

        binding.btnCancelarEquipo.setOnClickListener {
            limpiarCampos()
        }

        binding.btnCambiarModo.setOnClickListener {
            isModoJugador = !isModoJugador
            cambiarModoVisual()
        }

        obtenerEquipos(room)
        obtenerJugadores(room)
        cambiarModoVisual()
    }

    private fun agregarOActualizarJugador() {
        val nombre = binding.etNombre.text.toString().trim()
        val posicion = binding.etPosicion.text.toString().trim()
        val edad = binding.etEdad.text.toString().trim().toInt()
        val estatura = binding.etEstatura.text.toString().trim().toDouble()
        val dorsal = binding.etDorsal.text.toString().trim().toInt()
        val fechaNacimiento = binding.etFechaNacimiento.text.toString().trim()
        val convocado = binding.cbConvocado.isChecked

        if (nombre.isEmpty() || posicion.isEmpty() || edad == null || estatura == null || dorsal == null || fechaNacimiento.isEmpty() /*|| equipoSeleccionado == null*/) {
            Toast.makeText(this, "DEBES LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show()
            return
        }

        val jugador = Jugador(
            nombre = nombre,
            posicion = posicion,
            edad = edad,
            estatura = estatura,
            dorsal = dorsal,
            fechaNacimiento = fechaNacimiento,
            convocado = convocado,
            equipoId = equipoSeleccionado?.id ?: 0
        )

        lifecycleScope.launch {
            room.daoJugador().agregarJugador(jugador)
            obtenerJugadores(room)
            limpiarCampos()
        }
    }

    private fun agregarOActualizarEquipo() {
        val nombreEquipo = binding.etNombreEquipo.text.toString().trim()
        val pais = binding.etPais.text.toString().trim()
        val anioFundacion = binding.etAnioFundacion.text.toString().trim()

        if (nombreEquipo.isEmpty() || pais.isEmpty() || anioFundacion.isEmpty()) {
            Toast.makeText(this, "DEBES LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show()
            return
        }

        val equipo = Equipo(
            nombre = nombreEquipo,
            pais = pais,
            anioFundacion = anioFundacion
        )
        contadorIdEquipo++

        if (equipoSeleccionado == null) {
            lifecycleScope.launch {
                room.daoEquipo().agregarEquipo(equipo)
                obtenerEquipos(room)
                limpiarCampos()
            }
        } else {
            equipo.id = equipoSeleccionado!!.id
            lifecycleScope.launch {
                room.daoEquipo().actualizarEquipo(equipo)
                obtenerEquipos(room)
                limpiarCampos()
            }
        }
    }

    private fun obtenerEquipos(room: Database) {
        lifecycleScope.launch {
            val nuevosEquipos = room.daoEquipo().obtenerEquipos()
            listaEquipos.clear()
            listaEquipos.addAll(nuevosEquipos)
            adaptadorEquipos.notifyDataSetChanged()
            actualizarSpinnerEquipos()
        }
    }

    private fun obtenerJugadores(room: Database) {
        lifecycleScope.launch {
            val nuevosJugadores = room.daoJugador().obtenerJugadores()
            listaJugadores.clear()
            listaJugadores.addAll(nuevosJugadores)
            adaptadorJugadores.notifyDataSetChanged()
        }
    }

    private fun cambiarModoVisual() {
        if (isModoJugador) {
            binding.cvJugador.visibility = View.VISIBLE
            binding.cvEquipo.visibility = View.GONE
            binding.btnAddUpdate.text = "Agregar Jugador"
            binding.btnCancelarJugador.visibility = View.VISIBLE
            binding.btnCancelarEquipo.visibility = View.GONE
        } else {
            binding.cvJugador.visibility = View.GONE
            binding.cvEquipo.visibility = View.VISIBLE
            binding.btnAddUpdate.text = "Agregar Equipo"
            binding.btnCancelarJugador.visibility = View.GONE
            binding.btnCancelarEquipo.visibility = View.VISIBLE
        }

        limpiarCampos()
    }

    private fun limpiarCampos() {
        binding.etNombre.setText("")
        binding.etPosicion.setText("")
        binding.etEdad.setText("")
        binding.etEstatura.setText("")
        binding.etDorsal.setText("")
        binding.etFechaNacimiento.setText("")
        binding.cbConvocado.isChecked = false
        binding.etNombreEquipo.setText("")
        binding.etPais.setText("")
        binding.etAnioFundacion.setText("")
        equipoSeleccionado = null

        if (isModoJugador) {
            binding.spinnerEquipos.setSelection(0)
        }
    }

    private fun actualizarSpinnerEquipos() {
        val equipoNulo = Equipo(contadorIdEquipo,nombre = "Sin Equipo","","")
        contadorIdEquipo++
        listaEquipos.add(0, equipoNulo)

        val equiposArray = listaEquipos.map { it.nombre }.toTypedArray()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, equiposArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerEquipos.adapter = adapter

        binding.spinnerEquipos.setSelection(0)

        binding.spinnerEquipos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                equipoSeleccionado = if (position > 0) listaEquipos[position] else null
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada
            }
        }
    }

    override fun onEditItemClick(jugador: Jugador) {
        binding.btnAddUpdate.text = "Actualizar Jugador"
        binding.etNombre.setText(jugador.nombre)
        binding.etPosicion.setText(jugador.posicion)
        binding.etEdad.setText(jugador.edad.toString())
        binding.etEstatura.setText(jugador.estatura.toString())
        binding.etDorsal.setText(jugador.dorsal.toString())
        binding.etFechaNacimiento.setText(jugador.fechaNacimiento)
        binding.cbConvocado.isChecked = jugador.convocado

        equipoSeleccionado = listaEquipos.find { it.id == jugador.equipoId }
    }


    override fun onDeleteItemClick(jugador: Jugador) {
        lifecycleScope.launch {
            room.daoJugador().borrarJugador(jugador.nombre)
            adaptadorJugadores.notifyDataSetChanged()
            obtenerJugadores(room)
        }
    }

    override fun onEditItemClickEquipo(equipo: Equipo) {
        binding.btnAddUpdate.text = "Actualizar Equipo"
        binding.etNombreEquipo.setText(equipo.nombre)
        binding.etPais.setText(equipo.pais)
        binding.etAnioFundacion.setText(equipo.anioFundacion.toString())

        equipoSeleccionado = equipo
    }

    override fun onDeleteItemClickEquipo(equipo: Equipo) {
        lifecycleScope.launch {
            room.daoEquipo().borrarEquipo(equipo.nombre)
            adaptadorEquipos.notifyDataSetChanged()
            obtenerEquipos(room)
        }
    }
}
