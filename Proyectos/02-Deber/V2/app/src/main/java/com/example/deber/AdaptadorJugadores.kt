package com.example.deber


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.crud_room_kotlin.R

class AdaptadorJugadores (
    val listaJugadores: MutableList<Jugador>,
    val listener: AdaptadorListener
): RecyclerView.Adapter<AdaptadorJugadores.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_jugador, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jugador = listaJugadores[position]

        holder.tvNombre.text = jugador.nombre
        holder.tvPosicion.text = jugador.posicion
        holder.tvEdad.text = jugador.edad.toString()
        holder.tvEstatura.text = jugador.estatura.toString()
        holder.tvFechaNacimiento.text = jugador.fechaNacimiento
        holder.tvDorsal.text = jugador.dorsal.toString()
        if(jugador.convocado){
            holder.tvConvocado.text="Está Convocado"
        }else{
            holder.tvConvocado.text ="NO Está Convocado"
        }
//        holder.tvConvocado.text = jugador.convocado.toString()

        holder.cvJugador.setOnClickListener{
            listener.onEditItemClick(jugador)
        }
        holder.btnBorrar.setOnClickListener{
            listener.onDeleteItemClick(jugador)
        }
    }

    override fun getItemCount(): Int {
        return listaJugadores.size
    }
    inner class ViewHolder (ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val cvJugador = itemView.findViewById<CardView>(R.id.cvJugador)
        val tvNombre = itemView.findViewById<TextView>(R.id.tvNombre)
        val tvPosicion = itemView.findViewById<TextView>(R.id.tvPosicion)
        val tvEdad = itemView.findViewById<TextView>(R.id.tvEdad)
        val tvEstatura = itemView.findViewById<TextView>(R.id.tvEstatura)
        val tvDorsal = itemView.findViewById<TextView>(R.id.tvDorsal)
        val tvConvocado = itemView.findViewById<TextView>(R.id.tvConvocado)
        val tvFechaNacimiento = itemView.findViewById<TextView>(R.id.tvFechaNacimiento)
        val btnBorrar = itemView.findViewById<Button>(R.id.btnBorrar)
    }

}