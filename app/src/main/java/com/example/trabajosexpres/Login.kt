package com.example.trabajosexpres

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.example.trabajosexpres.Model.Token
import com.example.trabajosexpres.Volley.GsonRequest
import com.example.trabajosexpres.Volley.VolleySingleton
import org.json.JSONException
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
        val mapHeaders : MutableMap<String, String> = mutableMapOf<String,String>()
        mapHeaders.put ( "username" ,  login.username )
        mapHeaders.put ( "password" ,  login.password )
        val gsonRequest: GsonRequest<Token> = GsonRequest<Token>(
                url,
                Token::class.java,
                mapHeaders,
                requestSuccessListener(),
                requestErrorListener()
        )
        VolleySingleton.getInstance(this).addToRequestQueue(gsonRequest)
    }

    private fun requestSuccessListener(): Response.Listener<Token> {
        return Response.Listener<Token> { response ->
            token = response as Nothing?
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