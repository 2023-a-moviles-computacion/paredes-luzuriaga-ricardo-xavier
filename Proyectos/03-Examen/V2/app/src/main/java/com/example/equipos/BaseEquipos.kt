package com.example.equipos

import java.text.SimpleDateFormat
import java.util.*

class BaseEquipos {
    companion object{
        val arregloEquipos = arrayListOf<Equipo>()

        init {
            val formatoFecha = SimpleDateFormat("dd-MM-yyyy")
            val fechaInscripcion = formatoFecha.parse("10-07-2023")

            val jugadores1 = arrayListOf<Jugador>(
                Jugador(1, "Ricardo", 21, 30, "19-01-2002", 1.78, "Medio Centro Ofensivo - MCO", true,0),
                Jugador(2, "Jorge Luis", 28, 10, "22-10-1994", 1.75, "Medio Centro - MC", false,0 )
            )
            val jugadores2 = arrayListOf<Jugador>(
                Jugador(3, "Cristiano Ronaldo", 38, 7, "05-02-1985", 1.87, "Delantero - DC", true,1),
                Jugador(4, "Lionel Messi", 36, 10, "24-06-1987", 1.70, "Delantero - DC", true,1),
            )
            val jugadores3 = arrayListOf<Jugador>(
                Jugador(3, "Erling Haaland", 22, 9, "21-07-2000", 1.94, "Delantero - DC", true,2),
                Jugador(4, "Kylian Mbappé", 24, 7, "20-12-1998", 1.78, "Delantero - DC", true,2),
            )
            arregloEquipos.add(
                Equipo(1,"Paris Saint-Germain", "Francia", "12-08-1970", jugadores3)
            )
            arregloEquipos.add(
                Equipo(2,"Real Madrid", "España", "06-03-1902", jugadores2)
            )
            arregloEquipos.add(
                Equipo(3,"Borussia Dortmund", "Alemania", "19-12-1909", jugadores1)
            )
        }

    }
}