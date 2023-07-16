package com.example.myapplication


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DaoJugador {
    // Create
    @Insert
    suspend fun agregarJugador(jugador: Jugador)

    // Read
    @Query("SELECT * FROM jugadores")
    suspend fun obtenerJugadores(): MutableList<Jugador>

    // Update
    @Update
    suspend fun actualizarJugador(jugador: Jugador)

    @Query("UPDATE jugadores SET edad = :nuevaEdad, posicion = :nuevaPosicion, dorsal = :nuevoDorsal, convocado = :nuevoConvocado WHERE nombre = :nombreJugador")
    suspend fun actualizarJugador(nombreJugador: String, nuevaEdad: Int, nuevaPosicion: String, nuevoDorsal: Int, nuevoConvocado: Boolean)

    // Delete
    @Query("DELETE FROM jugadores WHERE nombre=:nombreJugador")
    suspend fun borrarJugador(nombreJugador: String)

    // Obtener jugadores de un equipo específico
    @Query("SELECT * FROM jugadores WHERE equipoId = :idEquipo")
    suspend fun obtenerJugadoresPorEquipo(idEquipo: Int): MutableList<Jugador>
}
