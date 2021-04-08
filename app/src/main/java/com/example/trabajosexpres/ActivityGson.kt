package com.example.trabajosexpres

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.Response
import com.example.trabajosexpres.Modelo.WeatherByCity
import com.example.trabajosexpres.Volley.GsonRequest
import com.example.trabajosexpres.Volley.VolleySingleton

class ActivityGson : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gson)
        BuscarClima();
    }

    fun BuscarClima(){
        val url = "api.openweathermap.org/data/2.5/weather?q=Oaxaca&appid=fba1e7690490ac34de04402055381e55"
        val mapHeaders : MutableMap<String, String> = mutableMapOf<String,String>() //agregar headers necesarios
        val myGsonRequest: GsonRequest<WeatherByCity> = GsonRequest<WeatherByCity>(
                url,
                WeatherByCity::class.java,
                mapHeaders,
                myRequestSuccessListener(), //Success Listener
                myRequestErrorListener() //Error Listener
        )
        VolleySingleton.getInstance(this).addToRequestQueue(myGsonRequest)
    }

    private fun myRequestSuccessListener(): Response.Listener<WeatherByCity> {
        return Response.Listener<WeatherByCity> {response ->
            // Tu código después de que recibiste la solicitud correctamente
            val  txtClima = findViewById<TextView>(R.id.txtClima)
            txtClima.text = "Hola"//response.weather.get(0).description
        }
    }

    private fun myRequestErrorListener(): Response.ErrorListener {
        return Response.ErrorListener {error ->
            Log.e("ERROR", error.toString())
            //Código en caso de error
        }
    }
}