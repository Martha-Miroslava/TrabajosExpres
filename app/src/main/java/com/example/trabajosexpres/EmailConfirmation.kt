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
import com.example.trabajosexpres.Model.CodeConfirmation
import com.example.trabajosexpres.Volley.VolleySingleton
import org.json.JSONObject

class EmailConfirmation : AppCompatActivity(){
    companion object {
        lateinit var code:CodeConfirmation
        fun getCodeConfirmation():CodeConfirmation { return code }
        fun setCodeConfirmation(newCode:CodeConfirmation) { code = newCode }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.email_confirmation)
    }

    fun sendCodeClicked(vew: View){
        val url = "http://10.0.2.2:5000/emails"
        val payload = JSONObject()
        payload.put("email", getCodeConfirmation().email)
        val request = HTTPRequest(
                Request.Method.POST,
                url,
                payload,
                Response.Listener {
                    sendMessage("El código se envio exitosamente")
                },
                requestErrorListener()
        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }

    fun acceptCodeClicked(vew: View){
        val textFieldCodeConfirmation =findViewById<TextView>(R.id.TextFieldCodeConfirmation)
        val codeUser:Int = textFieldCodeConfirmation.text.toString().toInt()
        val url = "http://10.0.2.2:5000/logins/validator"
        val payload = JSONObject()
        payload.put("username", getCodeConfirmation().username)
        payload.put("password", getCodeConfirmation().password)
        payload.put("code", codeUser)
        val request = HTTPRequest(
                Request.Method.PATCH,
                url,
                payload,
                Response.Listener {
                    sendMessage("La cuenta se registro exitosamente")
                    val login = Intent(this, Login::class.java)
                    startActivity(login)
                    finish()
                },
                requestErrorListener()
        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }

    private fun sendMessage(message: String) {
        Toast.makeText(this,  message, Toast.LENGTH_SHORT).show()
    }

    private fun requestErrorListener(): Response.ErrorListener {
        return Response.ErrorListener { error ->
            Log.e("ERROR", error.toString())
            sendMessage(error.toString())
        }
    }
}