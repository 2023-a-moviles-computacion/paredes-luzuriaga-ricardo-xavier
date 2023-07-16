package com.example.equipos

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class jugador_recicler_adaptador (
    private val contexto: Jugadores,
    private val lista: ArrayList<Jugador>,
    private val recyclerView: RecyclerView
    ): RecyclerView.Adapter<jugador_recicler_adaptador.MyViewHolder>(){
        inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}