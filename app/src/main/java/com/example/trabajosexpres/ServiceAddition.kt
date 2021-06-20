package com.example.trabajosexpres

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fluentvalidator.Validator
import br.com.fluentvalidator.context.ValidationResult
import com.android.volley.Request
import com.android.volley.Response
import com.example.trabajosexpres.HTTPRequest.HTTPRequest
import com.example.trabajosexpres.Model.Service
import com.example.trabajosexpres.Model.State
import com.example.trabajosexpres.Validator.ServiceValidator
import com.example.trabajosexpres.Volley.VolleySingleton
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ServiceAddition: AppCompatActivity() {
    var service = null
    var idMemberATE = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.service_addition)

    }

    fun searchStates(){
        val url = "http://10.0.2.2:5000/states/country/1"
        val request = HTTPRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                try {
                    var items:MutableList<State> = ArrayList()
                    var jsonArray: JSONArray? = null
                    jsonArray = it.getJSONArray("items")
                    for (i in 0 until jsonArray.length()) {
                        val objectGet = jsonArray.getJSONObject(i)
                        val state = State(
                            objectGet.getInt("idState"),
                            objectGet.getString("name"),
                            objectGet.getInt("idCountry"))
                        items.add(state)
                    }
                    val list: MutableList<String> = ArrayList()
                    for (state:State  in items){
                        list.add(state.name)
                    }
                    val adapter = ArrayAdapter(this, R.layout.list_item, list)
                    val editTextFilledExposedDropdown = findViewById<AutoCompleteTextView>(R.id.TextFieldState)
                    editTextFilledExposedDropdown.setAdapter(adapter)
                    editTextFilledExposedDropdown.setOnClickListener {
                        (it as AutoCompleteTextView).showDropDown()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace();
                }
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

    fun behindClicked(vew: View){
        val home = Intent(this, Home::class.java)
        startActivity(home)
        finish()
    }

    fun registerServiceClicked(view : View) {
        val  serviceAdd = createDataService()
        val validatorService: Validator<Service> = ServiceValidator()
        val  result: ValidationResult = validatorService.validate(serviceAdd);
        if(result.isValid){
            val url = "http://10.0.2.2:5000/services"
            val payload = JSONObject()
            payload.put("name", serviceAdd.name)
            payload.put("description", serviceAdd.descriptionService)
            payload.put("slogan", serviceAdd.slogan)
            payload.put("typeService", serviceAdd.typeService)
            payload.put("minimalCost", serviceAdd.minimalCost)
            payload.put("maximumCost", serviceAdd.maximumCost)
            payload.put("idCity", serviceAdd.idCity)
            payload.put("idMemberATE", serviceAdd.idMemberATE)
            val request = HTTPRequest(
                Request.Method.POST,
                url,
                payload,
                Response.Listener {
                    service = it.get("idService") as Nothing?
                    sendMessage("El servicio se registró exitosamente")
                },
                requestErrorListener()

            )
            VolleySingleton.getInstance(this).addToRequestQueue(request)
        }else{
            sendMessage(result.errors.elementAt(0).message)
        }
    }

    private fun createDataService():com.example.trabajosexpres.Model.Service {
        val textViewName =findViewById<TextView>(R.id.TextFieldName)
        val textViewDescription =findViewById<TextView>(R.id.TextFieldDescription)
        val textViewSlogan =findViewById<TextView>(R.id.TextFieldSlogan)
        val textViewTypeService =findViewById<TextView>(R.id.TextFieldType)
        val textViewMinimalCost =findViewById<TextView>(R.id.TextFieldInitialCost)
        val textViewMaximumCost =findViewById<TextView>(R.id.TextFieldFinalCost)
        val textViewWorkingHours = findViewById<TextView>(R.id.TextFieldWorkingHours)
        val idCity: Int = 1
        val memberATE = idMemberATE
        val name: String = textViewName.text.toString()
        val descriptionService: String = textViewDescription.text.toString()
        val slogan: String = textViewSlogan.text.toString()
        val typeService: String = textViewTypeService.text.toString()
        val minimalCost: String = textViewMinimalCost.text.toString()
        val minimalCostInt = minimalCost.toInt()
        val maximumCost: String = textViewMaximumCost.text.toString()
        val maximumCostInt = maximumCost.toInt()
        val workingHours: String = textViewWorkingHours.text.toString()
        val serviceAdd = Service(null, idCity, memberATE, name, minimalCostInt, maximumCostInt, descriptionService, slogan, typeService, workingHours, Service.ServiceStatus.ACTIVE)
        return serviceAdd
    }
    private fun sendMessage(message: String) {
        Toast.makeText(this,  message, Toast.LENGTH_SHORT).show()
    }
}