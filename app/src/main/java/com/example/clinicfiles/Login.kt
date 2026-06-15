package com.example.clinicfiles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import org.json.JSONObject

class Login : AppCompatActivity() {

    lateinit var login: Button
    lateinit var user: EditText
    lateinit var contra: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        user = findViewById(R.id.ETUSER)
        contra = findViewById(R.id.ETPASS)
        login = findViewById(R.id.btnlogin)

        // Registrar usuarios
        val profiles = getSharedPreferences("perfiles", MODE_PRIVATE)
        profiles.edit().apply {
            putString("JefedePiso","jefe123")
            putString("enfermer@","enf123")
            apply()
        }

        // Verificar si ya hay sesión activa
        val sesion = getSharedPreferences("sesion", MODE_PRIVATE)
        val rolGuardado = sesion.getString("rol", "")
        if (!rolGuardado.isNullOrEmpty()) {
            loginSegunRol(rolGuardado)
            return
        }

        login.setOnClickListener { logear() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun validarUsuario(nombreUsuario: String): Boolean {
        val profiles = getSharedPreferences("perfiles", MODE_PRIVATE)
        return profiles.contains(nombreUsuario)
    }

    private fun validarContraseña(nombreUsuario: String, contraseñaIngresada: String): Boolean {

        val profiles = getSharedPreferences("perfiles", MODE_PRIVATE)
        val contraseñaGuardada = profiles.getString(nombreUsuario, "")

        return contraseñaGuardada == contraseñaIngresada
    }


    private fun logear() {
        val nombreUsuario = user.text.toString().trim()
        val contraseña = contra.text.toString().trim()

        if (nombreUsuario.isEmpty()) {
            Toast.makeText(this, "Ingresa tu nombre de usuario", Toast.LENGTH_SHORT).show()
        } else if (contraseña.isEmpty()) {
            Toast.makeText(this, "Ingresa tu contraseña", Toast.LENGTH_SHORT).show()
        } else if (!validarUsuario(nombreUsuario)) {
            Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT).show()
        } else if (!validarContraseña(nombreUsuario, contraseña)) {
            Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
        } else {

            // El rol será el mismo nombre de usuario
            val rol = nombreUsuario

            val sesion = getSharedPreferences("sesion", MODE_PRIVATE)
            sesion.edit().apply {
                putString("usuario", nombreUsuario)
                putString("rol", rol)
                apply()
            }

            loginSegunRol(rol)
        }
    }

    private fun loginSegunRol(rol: String) {
        if (rol == "JefedePíso") {
            val destino = Intent(this, MainActivity::class.java)
            Toast.makeText(this, "Iniciaste sesion como $rol", Toast.LENGTH_LONG).show()
            startActivity(destino)
        } else if (rol == "emnfermer@") {
            val destino = Intent(this, Registros::class.java)
            Toast.makeText(this, "Iniciaste sesion como $rol", Toast.LENGTH_LONG).show()
            startActivity(destino)
        }
    }
}