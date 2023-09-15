package com.example.equipos

class InfoInicial {
    init {
        BaseDatos.tablaJugador!!.crearJugador("Cristiano Ronaldo", 38, 7, "05-02-1985", 1.87, "Delantero - DC", true, 1)
        BaseDatos.tablaJugador!!.crearJugador("Lionel Messi", 36, 10, "24-06-1987", 1.70, "Delantero - DC", true,1)
        BaseDatos.tablaJugador!!.crearJugador("Erling Haaland", 22, 9, "21-07-2000", 1.94, "Delantero - DC", true,2)

    }
}