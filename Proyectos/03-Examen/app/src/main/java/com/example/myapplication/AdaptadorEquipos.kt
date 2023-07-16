package com.example.myapplication


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.AdaptadorListener
import com.example.myapplication.Equipo
import com.example.myapplication.R


class AdaptadorEquipos (
    val listaEquipos: MutableList<Equipo>,
    val listener: AdaptadorListener
): RecyclerView.Adapter<AdaptadorEquipos.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_equipo, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val equipo = listaEquipos[position]

        holder.tvNombre.text = equipo.nombre
        holder.tvPais.text = equipo.pais
        holder.tvAnioFundacion.text = equipo.anioFundacion

        holder.cvEquipo.setOnClickListener {
            listener.onEditItemClickEquipo(equipo)
        }
        holder.btnBorrar.setOnClickListener {
            listener.onDeleteItemClickEquipo(equipo)
        }
    }

    override fun getItemCount(): Int {
        return listaEquipos.size
    }

    inner class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val cvEquipo = itemView.findViewById<CardView>(R.id.cvEquipo)
        val tvNombre = itemView.findViewById<TextView>(R.id.tvNombre)
        val tvPais = itemView.findViewById<TextView>(R.id.tvPais)
        val tvAnioFundacion = itemView.findViewById<TextView>(R.id.tvAnioFundacion)
        val btnBorrar = itemView.findViewById<Button>(R.id.btnBorrarEquipo)
    }
}
