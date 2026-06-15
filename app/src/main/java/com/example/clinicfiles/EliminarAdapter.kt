package com.example.clinicfiles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EliminarAdapter(private val lista: MutableList<Paciente>) :
    RecyclerView.Adapter<EliminarAdapter.EliminarViewHolder>() {

    // Guardamos el paciente, no la posición
    private val seleccionados = mutableSetOf<Paciente>()

    inner class EliminarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chbox = itemView.findViewById<CheckBox>(R.id.chbox)
        val tvname = itemView.findViewById<TextView>(R.id.tvname)
        val tvage = itemView.findViewById<TextView>(R.id.tvage)
        val tvdiagnostic = itemView.findViewById<TextView>(R.id.TVdiagnostic)
        val tvemergency = itemView.findViewById<TextView>(R.id.tvemergency)
    }
    // Aquí se crea el ViewHolder y se le asigna la vista inflada del layout item_borrar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EliminarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_borrar, parent, false)
        return EliminarViewHolder(view)
    }
    // para jalar los datos y se muestren en el holder
    override fun onBindViewHolder(holder: EliminarViewHolder, position: Int) {
        val paciente = lista[position]
        holder.tvname.text = paciente.Nombre
        holder.tvage.text = paciente.Edad.toString()
        holder.tvdiagnostic.text = paciente.Diagnostico
        holder.tvemergency.text = paciente.TelefonoEmergencia.toString()

        //inicalizamos el check box en null
        holder.chbox.setOnCheckedChangeListener(null)

        // Comparamos por objeto, no por posición
        holder.chbox.isChecked = seleccionados.contains(paciente)


        //aqui lo que se hace es que si esta marca el objeto se manda a una lista llamada seleccionados
        holder.chbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) seleccionados.add(paciente)
            else seleccionados.remove(paciente)
        }
    }
    // para saber cuentos elementos tiene mi lista
    override fun getItemCount(): Int = lista.size

    // Devuelve los índices de los pacientes seleccionados dentro de la lista actual.
// Solo incluye elementos que aún existen en la lista y los ordena de mayor a menor.
    fun getIndicesSeleccionados(): List<Int> =
        seleccionados
            .mapNotNull { paciente -> lista.indexOf(paciente).takeIf { it >= 0 } }
            .sortedDescending()
}