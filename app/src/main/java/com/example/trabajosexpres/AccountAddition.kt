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
import androidx.fragment.app.FragmentActivity
import br.com.fluentvalidator.Validator
import br.com.fluentvalidator.context.ValidationResult
import com.android.volley.Request
import com.android.volley.Response
import com.example.trabajosexpres.HTTPRequest.HTTPRequest
import com.example.trabajosexpres.Model.AccountRecover
import com.example.trabajosexpres.Model.MemberATE
import com.example.trabajosexpres.Model.State
import com.example.trabajosexpres.Validator.AccountRevoverValidator
import com.example.trabajosexpres.Validator.MemberATEValidator
import com.example.trabajosexpres.Volley.VolleySingleton
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class AccountAddition: AppCompatActivity() {
    var member = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_addition)
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
                            val objeto = jsonArray.getJSONObject(i)
                            val state = State(
                                    objeto.getInt("idState"),
                                    objeto.getString("name"),
                                    objeto.getInt("idCountry"))
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

    fun BehindClicked(vew: View){
        val login = Intent(this, Login::class.java)
        startActivity(login)
        finish()
    }

    fun registerAccountClicked(view : View) {
        val  MemberATE = createDataAccount()
        val validatorAccount: Validator<MemberATE> = MemberATEValidator()
        val  result: ValidationResult = validatorAccount.validate(MemberATE);
        if(result.isValid){
            val url = "http://10.0.2.2:5000/accounts"
            val payload = JSONObject()
            payload.put("username", MemberATE.username)
            payload.put("password", MemberATE.password)
            payload.put("name", MemberATE.name)
            payload.put("lastname", MemberATE.lastName)
            payload.put("dateBirth", MemberATE.dateBirth)
            payload.put("email", MemberATE.email)
            payload.put("idCity", MemberATE.idCity)
            payload.put("memberATEStatus", MemberATE.memberATEStatus)
            payload.put("memberATEType", MemberATE.accountType)
            val request = HTTPRequest(
                    Request.Method.POST,
                    url,
                    payload,
                    Response.Listener {
                        member = it.get("MemberATE") as Nothing?
                        sendMessage("La cuenta se registro exitosamente")
                    },
                    requestErrorListener()

            )
            VolleySingleton.getInstance(this).addToRequestQueue(request)
        }else{
            sendMessage(result.errors.elementAt(0).message)
        }
    }

    private fun createDataAccount():com.example.trabajosexpres.Model.MemberATE {
        val textViewName =findViewById<TextView>(R.id.TextFieldName)
        val textViewLastName =findViewById<TextView>(R.id.TextFieldLastName)
        val textViewDateBith =findViewById<TextView>(R.id.TextFieldDateBirth)
        val textViewEmail =findViewById<TextView>(R.id.TextFieldEmail)
        val textViewUserName =findViewById<TextView>(R.id.TextFieldUserName)
        val textViewPassword =findViewById<TextView>(R.id.TextFieldPassword)
        val idCity: Int = 1
        val userName: String = textViewUserName.text.toString()
        val password: String = textViewPassword.text.toString()
        val name: String = textViewName.text.toString()
        val lastname: String = textViewLastName.text.toString()
        val email: String = textViewEmail.text.toString()
        val date: String = textViewDateBith.text.toString()

        val memberATE = MemberATE(null, email, date, lastname, name, userName, password, 1,1, 1)
        return memberATE
    }
    private fun sendMessage(message: String) {
        Toast.makeText(this,  message, Toast.LENGTH_SHORT).show()
    }

}