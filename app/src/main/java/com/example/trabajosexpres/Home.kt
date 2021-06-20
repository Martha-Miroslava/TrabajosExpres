package com.example.trabajosexpres

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.fluentvalidator.Validator
import br.com.fluentvalidator.context.ValidationResult
import com.android.volley.Request
import com.android.volley.Response
import com.example.trabajosexpres.HTTPRequest.HTTPRequest
import com.example.trabajosexpres.Model.CodeConfirmation
import com.example.trabajosexpres.Model.MemberATE
import com.example.trabajosexpres.Validator.MemberATEValidator
import com.example.trabajosexpres.Volley.VolleySingleton
import org.json.JSONObject

class Home: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
    }

    fun registerAccountClicked(view : View) {
        val url = "http://10.0.2.2:5000/services/city/1"
        val request = HTTPRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener {
                },
                requestErrorListener()
        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }
    private fun requestErrorListener(): Response.ErrorListener {
        return Response.ErrorListener { error ->
            Log.e("ERROR", error.toString())
        }
    }
}