package com.example.equipos

class BaseJugadores {
    companion object{
        val arregloJugadores = arrayListOf<Jugador>()
        init {
                arregloJugadores.add(Jugador(3, "Erling Haaland", 22, 9, "21-07-2000", 1.94, "Delantero - DC", true,2))
                arregloJugadores.add(Jugador(4, "Kylian Mbappé", 24, 7, "20-12-1998", 1.78, "Delantero - DC", true,2))

        }

    }

}