package com.example.deber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.crud_room_kotlin.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), AdaptadorListener {
    lateinit var binding: ActivityMainBinding
    var listaJugadores: MutableList<Jugador> = mutableListOf()
    lateinit var adaptador: AdaptadorJugadores
    lateinit var room: Database
    lateinit var jugador: Jugador
    var aux: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvJugadores.layoutManager = LinearLayoutManager(this)

        room = Room.databaseBuilder(this,Database::class.java,"Database").build()

        obtenerJugadores(room)

        binding.btnAddUpdate.setOnClickListener{
            if(binding.etNombre.text.isNullOrEmpty() || binding.etDorsal.text.isNullOrEmpty()|| binding.etEdad.text.isNullOrEmpty()|| binding.etEstatura.text.isNullOrEmpty() || binding.etPosicion.text.isNullOrEmpty() || binding.etFechaNacimiento.text.isNullOrEmpty()){
                Toast.makeText(this, "DEBES LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.btnAddUpdate.text.equals("agregar")){
                jugador = Jugador(
                    binding.etNombre.text.toString().trim(),
                    binding.etEdad.text.toString().trim().toInt(),
                    binding.etDorsal.text.toString().trim().toInt(),
                    binding.etFechaNacimiento.text.toString().trim(),
                    binding.etEstatura.text.toString().trim().toDouble(),
                    binding.etPosicion.text.toString().trim(),
                    binding.cbConvocado.isChecked
                )
                agregarJugador(room, jugador)
            }else if(binding.btnAddUpdate.text.equals("actualizar")){
                jugador.edad = binding.etEdad.text.toString().trim().toInt()
                jugador.dorsal = binding.etDorsal.text.toString().trim().toInt()
                jugador.posicion = binding.etPosicion.text.toString().trim()
                jugador.convocado = binding.cbConvocado.isChecked
                actualizarJugador(room, jugador)
            }
        }
    }

    fun obtenerJugadores(room: Database){
        lifecycleScope.launch {
            listaJugadores = room.daoJugador().obtenerJugadores()
            adaptador = AdaptadorJugadores(listaJugadores,this@MainActivity)
            binding.rvJugadores.adapter = adaptador
        }
    }

    fun agregarJugador(room: Database, jugador: Jugador){
        lifecycleScope.launch {
            room.daoJugador().agregarJugador(jugador)
            obtenerJugadores(room)
            limpiarCampos()
        }
    }

    fun actualizarJugador(room: Database, jugador: Jugador){
        lifecycleScope.launch {
            room.daoJugador().actualizarJugador(jugador.nombre,jugador.edad, jugador.posicion, jugador.dorsal, jugador.convocado)
            obtenerJugadores(room)
            limpiarCampos()
        }
    }

    fun limpiarCampos(){
        jugador.edad=0
        jugador.nombre=""
        jugador.convocado=false
        jugador.dorsal=0
        jugador.estatura=0.0
        jugador.posicion=""
        jugador.fechaNacimiento=""
        binding.etNombre.setText("")
        binding.etDorsal.setText("")
        binding.etEdad.setText("")
        binding.etEstatura.setText("")
        binding.etPosicion.setText("")
        binding.etFechaNacimiento.setText("")

        if(binding.btnAddUpdate.text.equals("actualizar")){
            binding.btnAddUpdate.setText("agregar")
            binding.etNombre.isEnabled=true
        }
    }

    override fun onEditItemClick(jugador: Jugador) {
        binding.btnAddUpdate.setText("actualizar")
        binding.etNombre.isEnabled = false
        binding.etFechaNacimiento.isEnabled = false
        binding.etEstatura.isEnabled = false
        this.jugador = jugador
        binding.etNombre.setText(this.jugador.nombre)
        binding.etFechaNacimiento.setText(this.jugador.fechaNacimiento)
        binding.etEstatura.setText(this.jugador.estatura.toString())
        binding.etEdad.setText(this.jugador.edad.toString())
        binding.etDorsal.setText(this.jugador.dorsal.toString())
        binding.etPosicion.setText(this.jugador.posicion)

    }

    override fun onDeleteItemClick(jugador: Jugador) {
        lifecycleScope.launch {
            room.daoJugador().borrarJugador(jugador.nombre)
            adaptador.notifyDataSetChanged()
            obtenerJugadores(room)
        }
    }
}



