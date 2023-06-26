package com.example.deberricardoparedes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date


@Entity (tableName = "jugadores")
data class Jugador (
    @PrimaryKey var nombre: String,
    var fechaNacimiento: String,
    @ColumnInfo(name="edad") var edad: Int,
    var dorsal: Int,
    var estatura: Double,
    @ColumnInfo(name = "Posicion") var posicion: String,
    var convocado: Boolean
)