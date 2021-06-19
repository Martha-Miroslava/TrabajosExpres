package com.example.trabajosexpres

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.android.volley.Response
import com.example.trabajosexpres.Model.ListState
import com.example.trabajosexpres.Model.State
import com.example.trabajosexpres.Model.Token
import com.example.trabajosexpres.Volley.GsonRequest
import com.example.trabajosexpres.Volley.VolleySingleton

class AccountAddition: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchStates()
        setContentView(R.layout.account_addition)

    }

    fun searchStates(){
        val url = "http://10.0.2.2:5000/states/country/1"
        val mapHeaders : MutableMap<String, String> = mutableMapOf<String,String>()
        val gsonRequest: GsonRequest<ListState> = GsonRequest<ListState>(
                url,
                ListState::class.java,
                mapHeaders,
                requestSuccessListener(),
                requestErrorListener()
        )
        VolleySingleton.getInstance(this).addToRequestQueue(gsonRequest)
    }

    private fun requestSuccessListener(): Response.Listener<ListState> {
        return Response.Listener<ListState> { response ->
            /*val adapter = ArrayAdapter(requireContext(), R.layout.list_item, response)
            (textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)*/
        }
    }

    private fun requestErrorListener(): Response.ErrorListener {
        return Response.ErrorListener { error ->
            Log.e("ERROR", error.toString())
        }
    }

}