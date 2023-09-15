package com.example.equipos

import java.text.SimpleDateFormat
import java.util.Date

data class Equipo (
    var id: Int,
    var nombreEquipo: String?,
    var pais: String?,
    var fechaDeFundacion: String?,
    var jugadores:ArrayList<Jugador>?
        ){
    override fun toString(): String{
        return "Nombre del Equipo: ${nombreEquipo}\nPaís: ${pais}\nFecha de Fundación: ${fechaDeFundacion}\nJugadores Disponibles: \n${jugadores.toString()}\n"
    }

}