package com.example.myapplication

interface AdaptadorListener {
    fun onEditItemClick(jugador: Jugador)
    fun onDeleteItemClick(jugador: Jugador)
    fun onEditItemClickEquipo(equipo: Equipo)
    fun onDeleteItemClickEquipo(equipo: Equipo)
}
