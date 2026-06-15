package com.example.clinicfiles

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FichaAdapter(private val lista: MutableList<Paciente>) :
    RecyclerView.Adapter<FichaAdapter.FichaViewHolder>() {

    inner class FichaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvnombre = itemView.findViewById<TextView>(R.id.TVNombre)
        val tvdiagnostico  = itemView.findViewById<TextView>(R.id.TVDiagnostico)
    }
    // Aquí se crea el ViewHolder y se le asigna la vista inflada del layout item_holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FichaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ficha, parent, false)
        return FichaViewHolder(view)
    }


    // este es para jalar los datso registrados y salgan en el holder
    override fun onBindViewHolder(holder: FichaViewHolder, position: Int) {
        val paciente = lista[position]
        holder.tvnombre.text = paciente.Nombre
        holder.tvdiagnostico.text   = paciente.Diagnostico


        // nos sirve para al hacer click sobre el nombre de la empresa hacer el cambio a la tarjeta de datos emviandole la pos del registro para que abra esa tarjeta
        holder.tvnombre.setOnClickListener {
            val context= holder.itemView.context
            val tar= Intent(context, Ficha::class.java)
            tar.putExtra("pos",position)
            context.startActivity(tar)
        }
    }
    // para saber cuentos elementos tiene mi lista
    override fun getItemCount(): Int = lista.size
}