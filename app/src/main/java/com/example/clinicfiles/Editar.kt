package com.example.clinicfiles

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.content.edit

class Editar : AppCompatActivity() {

    private var indiceActual: Int = 0

    lateinit var spSexo: Spinner
    lateinit var spEstCivil: Spinner
    lateinit var spAlergias: Spinner
    lateinit var spSeguro: Spinner
    lateinit var spFumaoToma: Spinner

    lateinit var etNombre: EditText
    lateinit var etEdad: EditText
    lateinit var etDomicilio: EditText
    lateinit var etTelefono: EditText
    lateinit var etDiagnostico: EditText

    lateinit var btnAnterior: Button
    lateinit var btnGuardar: Button
    lateinit var btnSiguiente: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar)

        spSexo       = findViewById<Spinner>(R.id.spsexo)
        spEstCivil   = findViewById<Spinner>(R.id.spestcivil)
        spAlergias   = findViewById<Spinner>(R.id.spalergias)
        spSeguro     = findViewById<Spinner>(R.id.spseguro)
        spFumaoToma  = findViewById<Spinner>(R.id.spFumaoToma)

        etNombre = findViewById<EditText>(R.id.etNombre)
        etEdad = findViewById<EditText>(R.id.etEdad)
        etDomicilio = findViewById<EditText>(R.id.etDomicilio)
        etTelefono = findViewById<EditText>(R.id.etTelefono)
        etDiagnostico = findViewById<EditText>(R.id.etDiagnostico)


        btnAnterior  = findViewById<Button>(R.id.btnbefore)
        btnGuardar   = findViewById<Button>(R.id.btnrefresh)
        btnSiguiente = findViewById<Button>(R.id.btnafter)


        val sexo = resources.getStringArray(R.array.SEXO)
        val adapterSexo = ArrayAdapter(this, R.layout.spinning, R.id.textoSPIN, sexo)
        adapterSexo.setDropDownViewResource(R.layout.spinning)
        spSexo.adapter = adapterSexo

        spSexo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@Editar, "Sexo: ${sexo[position]}", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val estCivil = resources.getStringArray(R.array.ESTCIVIL)
        val adapterEstCivil = ArrayAdapter(this, R.layout.spinning, R.id.textoSPIN, estCivil)
        adapterEstCivil.setDropDownViewResource(R.layout.spinning)
        spEstCivil.adapter = adapterEstCivil

        spEstCivil.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@Editar, "Estado civil: ${estCivil[position]}", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val alergias = resources.getStringArray(R.array.ALERGIAS)
        val adapterAlergias = ArrayAdapter(this, R.layout.spinning, R.id.textoSPIN, alergias)
        adapterAlergias.setDropDownViewResource(R.layout.spinning)
        spAlergias.adapter = adapterAlergias

        spAlergias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@Editar, "Alergias: ${alergias[position]}", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val fumaToma = resources.getStringArray(R.array.FUMAoTOMA)
        val adapterFumaToma = ArrayAdapter(this, R.layout.spinning, R.id.textoSPIN, fumaToma)
        adapterFumaToma.setDropDownViewResource(R.layout.spinning)
        spFumaoToma.adapter = adapterFumaToma

        spFumaoToma.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@Editar, "Fuma/Toma: ${fumaToma[position]}", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val seguro = resources.getStringArray(R.array.Seguro)
        val adapterSeguro = ArrayAdapter(this, R.layout.spinning, R.id.textoSPIN, seguro)
        adapterSeguro.setDropDownViewResource(R.layout.spinning)
        spSeguro.adapter = adapterSeguro

        spSeguro.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@Editar, "Seguro: ${seguro[position]}", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }



        btnAnterior.setOnClickListener  { before() }
        btnGuardar.setOnClickListener   { changes() }
        btnSiguiente.setOnClickListener { after() }

        val toolbar: Toolbar? = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (PacientesLst.listaPacientes.isNotEmpty()) {
            mostrarPacienteEnPantalla()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun mostrarPacienteEnPantalla() {
        val paciente = PacientesLst.listaPacientes[indiceActual]
        etNombre.setText(paciente.Nombre)
        etEdad.setText(paciente.Edad.toString())
        etDomicilio.setText(paciente.Domicilio)
        etTelefono.setText(paciente.TelefonoEmergencia.toString())
        etDiagnostico.setText(paciente.Diagnostico)

        spSexo.seleccionarItem(paciente.Sexo)
        spEstCivil.seleccionarItem(paciente.EstCivil)
        spSeguro.seleccionarItem(paciente.Seguro)
        spAlergias.seleccionarItem(paciente.Alergias)
        spFumaoToma.seleccionarItem(paciente.FumaToma)
    }

    // Retrocede con el -1, Si está en el primer elemento, vuelve al último usando el operador módulo % ya que con ese sabe si estas en la primera pos y te manda a la ultima y sumamos la lista para que no nos den pos negativos
    private fun before() {
        if (PacientesLst.listaPacientes.isNotEmpty()) {
            indiceActual = (indiceActual - 1 + PacientesLst.listaPacientes.size) % PacientesLst.listaPacientes.size
            Toast.makeText(this, "Anterior", Toast.LENGTH_LONG).show()
            mostrarPacienteEnPantalla()
        }
    }
    // con el +1 avanza una pos hasta llegar al útlimo de ahí con %size si supera ese número regresa al primero
    private fun after() {
        if (PacientesLst.listaPacientes.isNotEmpty()) {
            indiceActual = (indiceActual + 1) % PacientesLst.listaPacientes.size
            Toast.makeText(this, "Siguiente", Toast.LENGTH_LONG).show()
            mostrarPacienteEnPantalla()
        }
    }

    private fun changes() {
        if (PacientesLst.listaPacientes.isEmpty()) {
            Toast.makeText(this, "No hay pacientes registrados para editar", Toast.LENGTH_LONG).show()
            return
        }
        //trim es para eliminar los espacios si es que el usuario registro con espacios
        val nombre = etNombre.text.toString().trim()
        val edad = etEdad.text.toString().trim()
        val domicilio = etDomicilio.text.toString().trim()
        val telefono = etTelefono.text.toString().trim()
        val diagnostico = etDiagnostico.text.toString().trim()

        if (nombre.isEmpty()) {
            Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }
        if (edad.isEmpty()) {
            Toast.makeText(this, "La edad no puede estar vacía", Toast.LENGTH_SHORT).show()
            return
        }
        if (domicilio.isEmpty()) {
            Toast.makeText(this, "El domicilio no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }
        if (telefono.isEmpty()) {
            Toast.makeText(this, "El teléfono no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }
        if (telefono.length != 10) {
            Toast.makeText(this, "El teléfono debe tener 10 dígitos", Toast.LENGTH_SHORT).show()
            return
        }
        if (diagnostico.isEmpty()) {
            Toast.makeText(this, "El diagnóstico no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        val pacienteActualizado = Paciente(
            Nombre             = nombre,
            Diagnostico        = diagnostico,
            Edad               = edad.toInt(),
            TelefonoEmergencia = telefono.toInt(),
            Domicilio          = domicilio,
            Sexo               = spSexo.selectedItem.toString(),
            EstCivil           = spEstCivil.selectedItem.toString(),
            Seguro             = spSeguro.selectedItem.toString(),
            Alergias           = spAlergias.selectedItem.toString(),
            FumaToma           = spFumaoToma.selectedItem.toString()
        )
        PacientesLst.listaPacientes[indiceActual] = pacienteActualizado
        Toast.makeText(this, "Paciente '${pacienteActualizado.Nombre}' actualizado", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this, "Ya estás en actulizar", Toast.LENGTH_LONG).show()
        }
        if (item.itemId == R.id.opc3) {
            val cambio = Intent(this, Registros::class.java)
            startActivity(cambio)
        }
        if(item.itemId==R.id.opc4)
        {
            val cambio = Intent(this, Eliminar::class.java )
            startActivity(cambio)
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

    //lo que entra en valor es lo que se asigno en registros
    fun Spinner.seleccionarItem(valor: String?) {

        //obtiene el adaptador relacionado a los valores de los spinners
        val adapter = this.adapter ?: return
        // Recorre todos los elementos del Spinner.
        for (i in 0 until adapter.count) {
            // Compara el elemento actual con el valor recibido.
            if (adapter.getItem(i).toString().equals(valor, ignoreCase = true)) {
                // cuando encuentra el que conicidencia lo seleccion y sale del for
                this.setSelection(i)
                return
            }
        }
    }
}