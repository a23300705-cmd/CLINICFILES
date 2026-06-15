package com.example.clinicfiles

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.net.Uri
import android.widget.Toast

class Ficha : AppCompatActivity() {
    private val REQUEST_CALL = 1

    lateinit var btnreturn: Button
    lateinit var btnLlamar: Button
    lateinit var tvname: TextView
    lateinit var tvage: TextView
    lateinit var tvsex: TextView
    lateinit var tvtelphone: TextView
    lateinit var tvdomicilie: TextView
    lateinit var tvdiagnostic: TextView
    lateinit var tvcivilstate: TextView
    lateinit var tvsmokeOdrink: TextView
    lateinit var tvalergies: TextView
    lateinit var tvsecure: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ficha)

        btnLlamar     = findViewById<Button>(R.id.btnllamar)
        tvname        = findViewById<TextView>(R.id.tvTMarca)
        tvage         = findViewById<TextView>(R.id.tvTPais)
        tvsex         = findViewById<TextView>(R.id.tvTEmpresa)
        tvtelphone    = findViewById<TextView>(R.id.tvTTelefono)
        tvdomicilie   = findViewById<TextView>(R.id.tvDomicilio)
        tvdiagnostic  = findViewById<TextView>(R.id.tvDiagnostico)
        tvcivilstate  = findViewById<TextView>(R.id.tvEstCivil)
        tvsmokeOdrink = findViewById<TextView>(R.id.tvFumaoToma)
        tvalergies    = findViewById<TextView>(R.id.tvAlergias)
        tvsecure      = findViewById<TextView>(R.id.tvSeguro)
        btnreturn     = findViewById<Button>(R.id.btnvolver)

        val posicion: Int
        posicion = intent.getIntExtra("pos", -1)
        val item = PacientesLst.listaPacientes[posicion]

        tvname.text        = item.Nombre
        tvage.text         = item.Edad.toString()
        tvsex.text         = item.Sexo
        tvtelphone.text    = item.TelefonoEmergencia.toString()
        tvdomicilie.text   = item.Domicilio
        tvdiagnostic.text  = item.Diagnostico
        tvcivilstate.text  = item.EstCivil
        tvsmokeOdrink.text = item.FumaToma
        tvalergies.text    = item.Alergias
        tvsecure.text      = item.Seguro


        btnLlamar.setOnClickListener { marcar() }
        btnreturn.setOnClickListener { volver() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun marcar()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.CALL_PHONE),REQUEST_CALL)
        }
        else{
            val llama=Intent(Intent.ACTION_CALL)
            //Convierte el número de teléfono en un formato válido para Android.
            llama.data=Uri.parse("tel:"+tvtelphone.text.toString())
            startActivity(llama)
        }
    }

    private fun volver(){
        val regresar = Intent(this, Registros::class.java)
        Toast.makeText(this, "Regresaste a registros", Toast.LENGTH_LONG).show()
        startActivity(regresar)
    }

}