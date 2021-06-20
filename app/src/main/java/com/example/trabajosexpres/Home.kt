package com.example.trabajosexpres

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.fluentvalidator.Validator
import br.com.fluentvalidator.context.ValidationResult
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.trabajosexpres.Adpater.AdapterService
import com.example.trabajosexpres.HTTPRequest.HTTPRequest
import com.example.trabajosexpres.Model.Service
import com.example.trabajosexpres.Volley.VolleySingleton
import com.google.android.material.appbar.MaterialToolbar
import org.json.JSONArray
import org.json.JSONObject
import java.io.Console

class Home: AppCompatActivity() {
    var listService: MutableList<Service> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        getService()
        val title = findViewById<MaterialToolbar>(R.id.TopAppBar)
        title.title = "!Bienvenido Usuario "+Login.getUsername()+" !"
    }

    fun getService() {
        val url = "http://10.0.2.2:5000/services/city/1"
        HTTPRequest.isArray = true
        HTTPRequest.token = intent.getStringExtra("token").toString()
        val request = HTTPRequest(
                Request.Method.GET,
                url,
                null,
                requestListener() ,
                requestErrorListener()
        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }

    private fun requestListener(): Response.Listener<JSONObject>{
        return Response.Listener {
            for (i in 0..it.length()-3){
                //println(it.get(i.toString()))
                val json:JSONObject = it.get(i.toString()) as JSONObject
                val idService:Int = json.get("idService") as Int
                val idCity:Int = json.get("idCity") as Int
                val idMemberATE: Int = json.get("idMemberATE") as Int
                val name: kotlin.String = json.get("name") as String
                val minimalCost: Double = json.get("minimalCost") as Double
                val maximumCost: Double = json.get("maximumCost") as Double
                val descriptionService: String= json.get("description") as String
                val slogan: kotlin.String= json.get("slogan") as String
                val typeService: kotlin.String= json.get("typeService") as String
                val workingHours: kotlin.String= json.get("workingHours") as String
                val serviceStatus: Int =  json.get("serviceStatus") as Int
                val service:Service = Service(idService, idCity,idMemberATE,name,minimalCost,maximumCost,descriptionService,
                slogan,typeService,workingHours,serviceStatus)
                listService.add(service)
            }
            val list = findViewById<ListView>(R.id.ListViewService)
            list.adapter = AdapterService(this,listService)
        }
    }

    private fun requestErrorListener(): Response.ErrorListener {
        return Response.ErrorListener { error ->
            Log.e("ERROR", error.toString())
        }
    }
}