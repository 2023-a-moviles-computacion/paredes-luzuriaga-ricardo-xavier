package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DaoEquipo {
    // Create
    @Insert
    suspend fun agregarEquipo(equipo: Equipo)

    // Read
    @Query("SELECT * FROM equipos")
    suspend fun obtenerEquipos(): MutableList<Equipo>

    // Update
    @Update
    suspend fun actualizarEquipo(equipo: Equipo)

    // Delete
    @Query("DELETE FROM equipos WHERE nombre = :nombreEquipo")
    suspend fun borrarEquipo(nombreEquipo: String)
}
