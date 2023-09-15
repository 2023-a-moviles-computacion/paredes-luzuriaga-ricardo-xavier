package com.example.equipos

class BaseDatos {
    companion object{
        var tablaEquipo:SQLHelperEquipo? = null
        var tablaJugador:SQLHelperJugadores? = null
        var equiposFire : ArrayList<Equipo> = arrayListOf()
        var jugadoresFire : ArrayList<Jugador> = arrayListOf()
    }
}