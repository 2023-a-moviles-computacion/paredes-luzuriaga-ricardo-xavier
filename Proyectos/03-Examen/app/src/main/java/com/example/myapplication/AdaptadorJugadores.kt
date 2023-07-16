package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R.*

class AdaptadorJugadores (
    val listaJugadores: MutableList<Jugador>,
    val listaEquipos: MutableList<Equipo>,
    val listener: AdaptadorListener
): RecyclerView.Adapter<AdaptadorJugadores.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(layout.item_rv_jugador, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jugador = listaJugadores[position]
        val equipo = listaEquipos.find { it.id == jugador.equipoId }

        holder.tvNombre.text = jugador.nombre
        holder.tvPosicion.text = jugador.posicion
        holder.tvEdad.text = jugador.edad.toString()
        holder.tvEstatura.text = jugador.estatura.toString()
        holder.tvFechaNacimiento.text = jugador.fechaNacimiento
        holder.tvDorsal.text = jugador.dorsal.toString()
        holder.tvConvocado.text = if (jugador.convocado) "Está Convocado" else "NO Está Convocado"
        holder.tvEquipo.text = equipo?.nombre ?: ""

        holder.cvJugador.setOnClickListener {
            listener.onEditItemClick(jugador)
        }
        holder.btnBorrar.setOnClickListener {
            listener.onDeleteItemClick(jugador)
        }
    }

    override fun getItemCount(): Int {
        return listaJugadores.size
    }

    inner class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val cvJugador = itemView.findViewById<CardView>(id.cvJugador)
        val tvNombre = itemView.findViewById<TextView>(id.tvNombre)
        val tvPosicion = itemView.findViewById<TextView>(id.tvPosicion)
        val tvEdad = itemView.findViewById<TextView>(id.tvEdad)
        val tvEstatura = itemView.findViewById<TextView>(id.tvEstatura)
        val tvDorsal = itemView.findViewById<TextView>(id.tvDorsal)
        val tvConvocado = itemView.findViewById<TextView>(id.tvConvocado)
        val tvFechaNacimiento = itemView.findViewById<TextView>(id.tvFechaNacimiento)
        val btnBorrar = itemView.findViewById<Button>(id.btnBorrar)
        val tvEquipo = itemView.findViewById<TextView>(id.tvEquipo)
    }
}
