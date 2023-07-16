package com.example.equipos

import java.text.SimpleDateFormat
import java.util.Date

data class Equipo (
    val id: Int,
    var nombreEquipo: String,
    var pais: String,
    var fechaDeFundacion: String,
    var jugadores:ArrayList<Jugador>
        ){
    override fun toString(): String{
        return "Los datos del equipo son:\nN# de Equipo: ${id}\nNombre: ${nombreEquipo}\nPaís: ${pais}\nFecha de Fundación: ${fechaDeFundacion}\nJugadores Disponibles: \n${jugadores.toString()}\n"
    }
}