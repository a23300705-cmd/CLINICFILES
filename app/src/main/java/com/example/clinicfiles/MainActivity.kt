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

class MainActivity : AppCompatActivity() {

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

    lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


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


        btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener { registrarPaciente() }

        val toolbar : androidx.appcompat.widget.Toolbar? = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        val sexo = resources.getStringArray(R.array.SEXO)
        val adapterSexo = ArrayAdapter(this, R.layout.spinning, R.id.textoSPIN, sexo)
        adapterSexo.setDropDownViewResource(R.layout.spinning)
        spSexo.adapter = adapterSexo

        spSexo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@MainActivity, "Sexo: ${sexo[position]}", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val estCivil = resources.getStringArray(R.array.ESTCIVIL)
        val adapterEstCivil = ArrayAdapter(this, R.layout.spinning, R.id.textoSPIN, estCivil)
        adapterEstCivil.setDropDownViewResource(R.layout.spinning)
        spEstCivil.adapter = adapterEstCivil

        spEstCivil.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@MainActivity, "Estado civil: ${estCivil[position]}", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val alergias = resources.getStringArray(R.array.ALERGIAS)
        val adapterAlergias = ArrayAdapter(this, R.layout.spinning, R.id.textoSPIN, alergias)
        adapterAlergias.setDropDownViewResource(R.layout.spinning)
        spAlergias.adapter = adapterAlergias

        spAlergias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@MainActivity, "Alergias: ${alergias[position]}", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val fumaToma = resources.getStringArray(R.array.FUMAoTOMA)
        val adapterFumaToma = ArrayAdapter(this, R.layout.spinning, R.id.textoSPIN, fumaToma)
        adapterFumaToma.setDropDownViewResource(R.layout.spinning)
        spFumaoToma.adapter = adapterFumaToma

        spFumaoToma.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@MainActivity, "Fuma/Toma: ${fumaToma[position]}", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val seguro = resources.getStringArray(R.array.Seguro)
        val adapterSeguro = ArrayAdapter(this, R.layout.spinning, R.id.textoSPIN, seguro)
        adapterSeguro.setDropDownViewResource(R.layout.spinning)
        spSeguro.adapter = adapterSeguro

        spSeguro.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@MainActivity, "Seguro: ${seguro[position]}", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun registrarPaciente() {
        val nombre = etNombre.text.toString().trim()
        val edad = etEdad.text.toString().trim()
        val domicilio = etDomicilio.text.toString().trim()
        val diagnostico = etDiagnostico.text.toString().trim()
        val telefono = etTelefono.text.toString().trim()

        if (nombre.isEmpty()) {
            Toast.makeText(this, "Ingresa el nombre", Toast.LENGTH_SHORT).show()
            return
        }
        if (edad.isEmpty()) {
            Toast.makeText(this, "Ingresa la edad", Toast.LENGTH_SHORT).show()
            return
        }
        if (domicilio.isEmpty()) {
            Toast.makeText(this, "Ingresa el domicilio", Toast.LENGTH_SHORT).show()
            return
        }
        if (telefono.isEmpty()) {
            Toast.makeText(this, "Ingresa el teléfono", Toast.LENGTH_SHORT).show()
            return
        }
        if (telefono.length != 10) {
            Toast.makeText(this, "El teléfono debe tener 10 dígitos", Toast.LENGTH_SHORT).show()
            return
        }

        val paciente = Paciente(
            nombre,
            diagnostico,
            edad.toInt(),
            telefono.toInt(),
            domicilio,
            spSexo.selectedItem.toString(),
            spEstCivil.selectedItem.toString(),
            spSeguro.selectedItem.toString(),
            spAlergias.selectedItem.toString(),
            spFumaoToma.selectedItem.toString()
        )

        PacientesLst.listaPacientes.add(paciente)
        Toast.makeText(this, "Registrado: ${paciente.Nombre}", Toast.LENGTH_SHORT).show()

        etNombre.text.clear()
        etDiagnostico.text.clear()
        etEdad.text.clear()
        etTelefono.text.clear()
        etDomicilio.text.clear()
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
            Toast.makeText(this,"Ya estás en registrar", Toast.LENGTH_LONG).show()

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

}