package com.example.deber


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "jugadores")
data class Jugador(
    @PrimaryKey var nombre: String,
    var edad: Int,
    var dorsal: Int,
    var fechaNacimiento: String,
    var estatura: Double,
    var posicion: String,
    var convocado: Boolean
)