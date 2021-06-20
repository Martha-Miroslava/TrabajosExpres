package com.example.trabajosexpres

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fluentvalidator.Validator
import br.com.fluentvalidator.context.ValidationResult
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.example.trabajosexpres.HTTPRequest.HTTPRequest
import com.example.trabajosexpres.Model.Account
import com.example.trabajosexpres.Model.AccountRecover
import com.example.trabajosexpres.Validator.AccountRevoverValidator
import com.example.trabajosexpres.Validator.AccountValidator
import com.example.trabajosexpres.Volley.VolleySingleton
import org.json.JSONObject


class AccountRecovery: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_recovery)
    }

    fun SendCodeAccountClicked(view : View) {
        val textFieldEmail =findViewById<TextView>(R.id.TextFieldEmail)
        val email: String = textFieldEmail.text.toString()
        val account = Account(email)
        val validatorAccount: Validator<Account> = AccountValidator()
        val  result: ValidationResult = validatorAccount.validate(account);
        if(result.isValid){
            val url = "http://10.0.2.2:5000/emails/password"
            val payload:JSONObject = JSONObject()
            payload.put("email", email)
            val request = HTTPRequest(
                    Request.Method.POST,
                    url,
                    payload,
                    Response.Listener {
                        sendMessage("El código se envío exitosamente")
                    },
                    requestErrorListener()
            )
            VolleySingleton.getInstance(this).addToRequestQueue(request)
        }else{
            sendMessage(result.errors.elementAt(0).message)
        }
    }

    fun ChangeAccountClicked(view : View) {
        val textFieldEmail =findViewById<TextView>(R.id.TextFieldEmail)
        val email: String = textFieldEmail.text.toString()
        val textFieldPassword =findViewById<TextView>(R.id.TextFieldPassword)
        val password: String = textFieldPassword.text.toString()
        val textFieldConfirmationPassword =findViewById<TextView>(R.id.TextFieldConfirmationPassword)
        val confirmationPassword: String = textFieldConfirmationPassword.text.toString()
        val textFieldCodeConfirmation =findViewById<TextView>(R.id.TextFieldCodeConfirmation)
        val code: String = textFieldCodeConfirmation.text.toString()
        val codeFinal: Int = code.toIntOrNull()!!
        val account = com.example.trabajosexpres.Model.AccountRecover(email, password, codeFinal)
        val validatorAccount: Validator<AccountRecover> = AccountRevoverValidator()
        val  result: ValidationResult = validatorAccount.validate(account);
        if(result.isValid){
            if(password.equals(confirmationPassword)) {
                val url = "http://10.0.2.2:5000/logins/validatePassword"
                val payload: JSONObject = JSONObject()
                payload.put("email", email)
                payload.put("password", password)
                payload.put("code", codeFinal)
                val request = HTTPRequest(
                        Request.Method.PATCH,
                        url,
                        payload,
                        Response.Listener {
                            sendMessage("La cuenta se recuperó exitosamente")
                        },
                        requestErrorListener()
                )
                VolleySingleton.getInstance(this).addToRequestQueue(request)
            }else{
                sendMessage("La cofirmación debe ser la misma que la contraseña")
            }
        }else{
            sendMessage(result.errors.elementAt(0).message)
        }
    }

    private fun requestErrorListener(): Response.ErrorListener {
        return Response.ErrorListener { error ->
            Log.e("ERROR", error.toString())
            sendMessage("Ocurrio un problema. Intente más tarde")
        }
    }

    private fun sendMessage(message: String) {
        Toast.makeText(this,  message, Toast.LENGTH_SHORT).show()
    }
}