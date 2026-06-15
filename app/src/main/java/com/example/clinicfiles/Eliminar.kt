package com.example.clinicfiles

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Eliminar : AppCompatActivity() {
    lateinit var recy: RecyclerView
    lateinit var btnEliminar: Button

    lateinit var adapter: EliminarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eliminar)

        recy = findViewById<RecyclerView>(R.id.rv2)

        if (PacientesLst.listaPacientes.isEmpty()) {
            recy.visibility = android.view.View.GONE
            Toast.makeText(this, "No hay cafés registrados", Toast.LENGTH_LONG).show()
        } else {
            recy.layoutManager = LinearLayoutManager(this)
            adapter = EliminarAdapter(PacientesLst.listaPacientes)
            recy.adapter = adapter
            adapter.notifyDataSetChanged()
        }



        btnEliminar = findViewById(R.id.btnborrar)
        btnEliminar.setOnClickListener { eliminar() }

        val toolbar : Toolbar? = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun eliminar() {
        //pide la lista de seleccionados
        val indices = adapter.getIndicesSeleccionados()

        if (indices.isEmpty()) {
            Toast.makeText(this, "Selecciona al menos un café", Toast.LENGTH_LONG).show()
            return
        }
        //eliman los registros en el orden en el que esta la lista seleccion y al final actulizar los indices y notificar al adapter del cambio
        indices.sortedDescending().forEach { i ->
            PacientesLst.listaPacientes.removeAt(i)
        }

        adapter.notifyDataSetChanged()

        if (PacientesLst.listaPacientes.isEmpty()) {
            recy.visibility = android.view.View.GONE
            Toast.makeText(this, "Ya no hay cafés registrados", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "${indices.size} café eliminado", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val sesion = getSharedPreferences("sesion", MODE_PRIVATE)
        val rol = sesion.getString("rol", "")

        if (rol == "JefedePiso") {
            menu?.findItem(R.id.opc6)?.isVisible = false
            menu?.findItem(R.id.opc7)?.isVisible = false
        } else if (rol == "enfermer@") {

            menu?.findItem(R.id.opc1)?.isVisible = false
            menu?.findItem(R.id.opc2)?.isVisible = false
            menu?.findItem(R.id.opc4)?.isVisible = false
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.opc1) {
            val cambio = Intent(this, MainActivity::class.java)
            startActivity(cambio)

        }
        if (item.itemId == R.id.opc2) {
            val cambio = Intent(this, Editar::class.java)
            startActivity(cambio)

        }
        if (item.itemId == R.id.opc3) {
            val cambio = Intent(this, Registros::class.java)
            startActivity(cambio)
        }
        if(item.itemId==R.id.opc4)
        {
            Toast.makeText(this, "Ya estás en eliminar", Toast.LENGTH_LONG).show()
        }
        if (item.itemId == R.id.opc5) {
            val sesion = getSharedPreferences("sesion", MODE_PRIVATE)
            val nombreUsuario = sesion.getString("usuario", "Desconocido")
            sesion.edit().clear().apply()

            Toast.makeText(this, "Cerraste sesión como $nombreUsuario", Toast.LENGTH_LONG).show()

            val cambio = Intent(this, Login::class.java)
            startActivity(cambio)
            this.finishAffinity()
        }
        if (item.itemId == R.id.opc6) {
            val cambio = Intent(this, Creador::class.java)
            startActivity(cambio)
        }
        if (item.itemId == R.id.opc7) {
            val cambio = Intent(this, Contacto::class.java)
            startActivity(cambio)
        }

        return super.onOptionsItemSelected(item)
    }
}