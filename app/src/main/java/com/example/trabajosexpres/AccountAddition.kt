package com.example.trabajosexpres

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
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

class AccountAddition: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_addition)
    }

    private fun requestErrorListener(): Response.ErrorListener {
        return Response.ErrorListener { error ->
            Log.e("ERROR", error.toString())
            sendMessage(error.toString())
        }
    }

    fun behindClicked(vew: View){
        val login = Intent(this, Login::class.java)
        startActivity(login)
        finish()
    }

    fun registerAccountClicked(view : View) {
        val  MemberATE = createDataAccount()
        val validatorAccount: Validator<MemberATE> = MemberATEValidator()
        val  result: ValidationResult = validatorAccount.validate(MemberATE);
        if(result.isValid){
            val textViewPassword =findViewById<TextView>(R.id.TextFieldConfirmationPassword)
            if(MemberATE.password.equals(textViewPassword.text.toString())) {
                val checkboxRegister =findViewById<CheckBox>(R.id.CheckboxRegister)
                val isAccept: Boolean = checkboxRegister.isChecked
                if(isAccept) {
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
                                val emailConfirmation = Intent(this, EmailConfirmation::class.java)
                                val newCode:CodeConfirmation = CodeConfirmation(MemberATE.username, MemberATE.password, MemberATE.email)
                                EmailConfirmation.setCodeConfirmation(newCode)
                                startActivity(emailConfirmation)
                                finish()
                            },
                            requestErrorListener()
                    )
                    VolleySingleton.getInstance(this).addToRequestQueue(request)
                }
                else{
                    sendMessage("Por favor acepte los terminos")
                }
            }
            else{
                sendMessage("La cofirmación debe ser la misma que la contraseña")
            }
        }else{
            sendMessage(result.errors.elementAt(0).message)
        }
    }

    private fun createDataAccount():MemberATE {
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
        val memberATE = MemberATE(null, email, date, lastname, name, userName, password, 1,idCity, 1)
        return memberATE
    }
    private fun sendMessage(message: String) {
        Toast.makeText(this,  message, Toast.LENGTH_SHORT).show()
    }

}