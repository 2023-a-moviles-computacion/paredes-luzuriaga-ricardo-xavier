package com.example.deber


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
    @Query("UPDATE jugadores SET edad = :nuevaEdad, posicion = :nuevaPosicion, dorsal = :nuevoDorsal, convocado = :nuevoConvocado WHERE nombre = :nombreJugador")
    suspend fun actualizarJugador(nombreJugador: String, nuevaEdad: Int, nuevaPosicion: String, nuevoDorsal: Int, nuevoConvocado: Boolean)

    //Delete
    @Query("DELETE FROM jugadores WHERE nombre=:nombreJugador")
    suspend fun borrarJugador(nombreJugador: String)
}

/*
*var edad: Int,
    var dorsal: Int,
    var fechaNacimiento: String,
    var estatura: Double,
    var posicion: String,
    var convocado: Boolean
* */