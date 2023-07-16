package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipos")
data class Equipo(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    val nombre: String,
    val pais: String,
    val anioFundacion: String
)