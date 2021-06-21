package com.example.trabajosexpres

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.example.trabajosexpres.HTTPRequest.HTTPRequest
import com.example.trabajosexpres.Volley.VolleySingleton
import org.json.JSONObject

class RequestFirstEmployee: AppCompatActivity() {
    var option = 0
    var idRequest = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_fist_employee)

    }

    fun acceptRequestClicked(vew: View)
    {
        option = 2
        sendToApi()
        goHome()
    }

    fun rejectRequestClicked(vew: View){
        option = 3
        sendToApi()
        goHome()

    }

    fun goHome(){
        val home = Intent(this, HomeEmployee::class.java)
        startActivity(home)
        finish()
    }

    fun sendToApi(){
        val url = "http://10.0.2.2:5000/requests/$idRequest"
        val payload = JSONObject()
        payload.put("requestStatus", option)
        val request = HTTPRequest(
            Request.Method.PATCH,
            url,
            payload,
            Response.Listener {

            },
            requestErrorListener()
        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }

    private fun requestErrorListener(): Response.ErrorListener {
        return Response.ErrorListener { error ->
            Log.e("ERROR", error.toString())
            sendMessage(error.toString())
        }
    }

    private fun sendMessage(message: String) {
        Toast.makeText(this,  message, Toast.LENGTH_SHORT).show()
    }

}