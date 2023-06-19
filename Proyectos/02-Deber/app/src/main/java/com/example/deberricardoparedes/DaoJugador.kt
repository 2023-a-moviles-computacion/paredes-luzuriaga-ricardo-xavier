package com.example.deberricardoparedes
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoJugador {
    //Método CRUD
    //Create
    @Insert
    suspend fun agregarJugador(jugador: Jugador)
    //Read
    @Query ("SELECT * FROM jugadores")
    suspend fun obtenerJugadores(): MutableList<Jugador>
    //Update
    @Query ("UPDATE jugadores set edad=:nuevaEdad WHERE nombre=:nombreJugador")
    suspend fun actualizarJugador(nombreJugador: String, nuevaEdad: Int)
    //Delete
    @Query("DELETE FROM jugadores WHERE nombre=:nombreJugador")
    suspend fun borrarJugador(nombreJugador: String)
}