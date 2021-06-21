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
import com.example.trabajosexpres.Volley.VolleySingleton
import org.json.JSONObject


class Login : AppCompatActivity() {
	companion object {
		lateinit var usernameAccount: String
		fun getUsername(): String { return usernameAccount }
		fun setUsername(user: String) { usernameAccount = user }

		lateinit var passwordUser: String
		fun getPassword(): String { return passwordUser }
		fun setPassword(passwordAccount: String) { passwordUser = passwordAccount }
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.login)
	}

	fun searchAccount() {
		val url = "http://10.0.2.2:5000/logins"
		val login = createDataLogin()
		val payload = JSONObject()
		payload.put("username", login.username)
		payload.put("password", login.password)
		val request = HTTPRequest(
			Request.Method.POST,
			url,
			payload,
			responseListener(),
			requestErrorListener()

		)
		VolleySingleton.getInstance(this).addToRequestQueue(request)
	}

	private fun responseListener(): Response.Listener<JSONObject> {
		return Response.Listener { response ->
			var intent = Intent(this, HomeEmployee::class.java)
			when (response.getInt("memberATEType")) {
				1 -> intent = Intent(this, Home::class.java) // TODO aqui va el cliente
				2 -> intent = Intent(this, HomeEmployee::class.java)
				3 -> Toast.makeText(
					this,
					"Los administradores aun no están soportados",
					Toast.LENGTH_SHORT
				).show()
			}
			if (response.getInt("memberATEType") < 3) {
				intent.putExtra("token", response.getString("token"))
				intent.putExtra("memberATEType", response.getInt("memberATEType"))
				intent.putExtra("idMemberATE", response.getInt("idMemberATE"))
				intent.putExtra("idCity", response.getInt("idCity"))
				startActivity(intent)
				finish()
			}
		}
	}

	private fun requestErrorListener(): Response.ErrorListener {
		return Response.ErrorListener { error ->
			Log.e("ERROR", error.toString())
			sendMessage("Ocurrio un problema. Intente más tarde")
		}
	}

	fun LoginClicked(view: View) {
		searchAccount()
	}

	fun registerAccountClicked(view: View) {
		val accountAddition = Intent(this, AccountAddition::class.java)
		startActivity(accountAddition)
		finish()
	}

	fun RecoverAccountClicked(view: View) {
		val accountRecovery = Intent(this, AccountRecovery::class.java)
		startActivity(accountRecovery)
		finish()
	}

	private fun createDataLogin(): com.example.trabajosexpres.Model.Login {
		val textViewUserName = findViewById<TextView>(R.id.TextFieldUserName)
		val textViewPassword = findViewById<TextView>(R.id.TextFieldPassword)
		val userName: String = textViewUserName.text.toString()
		val password: String = textViewPassword.text.toString()
		Login.setUsername(userName)
		Login.setPassword(password)
		val login = com.example.trabajosexpres.Model.Login(userName, password)
		return login
	}

	private fun sendMessage(message: String) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
	}
}