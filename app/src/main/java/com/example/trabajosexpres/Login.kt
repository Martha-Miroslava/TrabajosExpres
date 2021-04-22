package com.example.trabajosexpres

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val button=findViewById<Button>(R.id.ButtonLogIn)
        button.setOnClickListener {
            val textViewUserName =findViewById<TextView>(R.id.TextFieldUserName)
            val textViewPassword =findViewById<TextView>(R.id.TextFieldPassword)
            val userName: String = textViewUserName.text.toString()
            val password: String = textViewPassword.text.toString()

            if(!userName.isNullOrEmpty() && password.isNullOrEmpty()) {
                sendMessage("Registrado con exito")
            } else {
                sendMessage("Ingrese datos correctos")
            }
        }
    }
    private fun sendMessage(message: String) {
        Toast.makeText(this,  message, Toast.LENGTH_SHORT).show()
    }
}