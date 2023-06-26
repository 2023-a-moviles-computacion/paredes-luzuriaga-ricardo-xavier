package com.example.deber


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.crud_room_kotlin.R
import org.w3c.dom.Text

class AdaptadorJugadores (
    val listaJugadores: MutableList<Jugador>,
    val listener: AdaptadorListener
): RecyclerView.Adapter<AdaptadorJugadores.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_jugador,parent,false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jugador = listaJugadores[position]

        /*
        Esta es la parte que no me funcionaaaaa*/
        holder.tvNombre.text = jugador.nombre
        holder.tvEdad.text = jugador.edad.toString()

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
        val tvEdad = itemView.findViewById<TextView>(R.id.tvEdad)
        val btnBorrar = itemView.findViewById<CardView>(R.id.btnBorrar)
    }

}