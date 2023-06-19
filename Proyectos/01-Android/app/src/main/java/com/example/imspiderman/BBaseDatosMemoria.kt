package com.example.imspiderman

class BBaseDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init{
            arregloBEntrenador.add(BEntrenador(1,"Adrian","a@aa.com"))
            arregloBEntrenador.add(BEntrenador(2,"Ricardo","r@aa.com"))
            arregloBEntrenador.add(BEntrenador(3,"PedroUwU","p@aa.com"))
        }
    }
}