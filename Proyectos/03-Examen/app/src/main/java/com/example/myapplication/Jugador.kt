package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "jugadores",
    foreignKeys = [ForeignKey(
        entity = Equipo::class,
        parentColumns = ["id"],
        childColumns = ["equipoId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Jugador(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var nombre: String,
    var edad: Int,
    var dorsal: Int,
    var fechaNacimiento: String,
    var estatura: Double,
    var posicion: String,
    var convocado: Boolean,
    @ColumnInfo(name = "equipoId", index = true) var equipoId: Int
)

