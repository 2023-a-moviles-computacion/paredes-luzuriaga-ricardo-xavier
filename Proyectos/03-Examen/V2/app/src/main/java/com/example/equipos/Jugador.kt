package com.example.equipos

import java.text.SimpleDateFormat

class Jugador (
    val id:Int,
    var nombre: String?,
    var edad: Int?,
    var dorsal: Int?,
    var fechaNacimiento: String?,
    var estatura: Double?,
    var posicion: String?,
    var convocado: Boolean
){
    override fun toString(): String {
        val aux:String
        if(convocado){
            aux = "Está Convocado"
        }else{
            aux = "No Está Convocado"
        }
        return "El jugador tiene la siguiente información:\nJugador N#: ${id}\nNombre: ${nombre}\n" +
                "Nombre: ${nombre}\nDorsal: ${dorsal}\nEdad: ${edad}\nEstatura: ${estatura} [m]\nPosición: ${posicion}\nFecha de Nacimiento: ${fechaNacimiento}\nConvocado: ${aux}\n"
    }
}