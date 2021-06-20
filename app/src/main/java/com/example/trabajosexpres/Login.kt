package com.example.trabajosexpres

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.example.trabajosexpres.HTTPRequest.HTTPRequest
import com.example.trabajosexpres.Volley.VolleySingleton
import org.json.JSONObject


class Login : AppCompatActivity() {
    var token = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
    }

    fun searchAccount(){
        val url = "http://10.0.2.2:5000/logins"
        val login = createDataLogin()
        val payload = JSONObject()
        payload.put("username", login.username)
        payload.put("password", login.password)
        val request = HTTPRequest(
                Request.Method.POST,
                url,
                payload,
                Response.Listener {
                    token = it.get("token") as Nothing?
                },
                requestErrorListener()

        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }

    private fun requestSuccessListener(): Response.Listener<JSONObject> {
        return Response.Listener { response ->
            token = response.get("token") as Nothing?
        }
    }

    private fun requestErrorListener(): Response.ErrorListener {
        return Response.ErrorListener { error ->
            Log.e("ERROR", error.toString())
        }
    }

    fun LoginClicked(view : View) {
        searchAccount()
    }

    fun registerAccountClicked(view : View) {
        val accountAddition = Intent(this, AccountAddition::class.java)
        startActivity(accountAddition)
        finish()
    }

    fun RecoverAccountClicked(view : View) {
        val accountRecovery = Intent(this, AccountRecovery::class.java)
        startActivity(accountRecovery)
        finish()
    }

    private fun createDataLogin():com.example.trabajosexpres.Model.Login {
        val textViewUserName =findViewById<TextView>(R.id.TextFieldUserName)
        val textViewPassword =findViewById<TextView>(R.id.TextFieldPassword)
        val userName: String = textViewUserName.text.toString()
        val password: String = textViewPassword.text.toString()
        val login = com.example.trabajosexpres.Model.Login(userName,password)
        return login
    }

    private fun sendMessage(message: String) {
        Toast.makeText(this,  message, Toast.LENGTH_SHORT).show()
    }
}