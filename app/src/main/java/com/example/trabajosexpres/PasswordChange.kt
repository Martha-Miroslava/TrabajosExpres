package com.example.trabajosexpres

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import br.com.fluentvalidator.Validator
import br.com.fluentvalidator.context.ValidationResult
import com.android.volley.Request
import com.android.volley.Response
import com.example.trabajosexpres.HTTPRequest.HTTPRequest
import com.example.trabajosexpres.Model.Password
import com.example.trabajosexpres.Validator.PasswordValidator
import com.example.trabajosexpres.Volley.VolleySingleton
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject

class PasswordChange: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: Toolbar
    lateinit var password: String
    lateinit var newPassword : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.password_change)

        drawerLayout = findViewById(R.id.drawer_layout_change_account)
        navigationView = findViewById(R.id.NavegationViewUser)
        toolbar = findViewById(R.id.toolbarClient)
        toolbar.title = "!Bienvenido Usuario "+Login.getUsername()+" !"
        setSupportActionBar(toolbar)
        navigationView.setNavigationItemSelectedListener(this)
    }

    fun changePasswordClicked(vew: View){
        val textViewPassword =findViewById<TextView>(R.id.TextFieldCurrentPassword)
        val textViewNewPassword =findViewById<TextView>(R.id.TextFieldNewPassword)
        val textViewConfirmation =findViewById<TextView>(R.id.TextFieldConfirmationPassword)
        newPassword = textViewNewPassword.text.toString()
        password = textViewPassword.text.toString()
        val confirmation: String = textViewConfirmation.text.toString()
        if(password.equals(Login.getPassword())){
            if(newPassword.equals(confirmation)){
                val passwordAccount:Password = Password(password)
                val validatorAccount: Validator<Password> = PasswordValidator()
                val  result: ValidationResult = validatorAccount.validate(passwordAccount);
                if(result.isValid){
                    changePassword()
                }else{
                    sendMessage(result.errors.elementAt(0).message)
                }
            }else{
                sendMessage("La contraseña no es la misma que la confirmación")
            }
        }else{
            sendMessage("La contraseña actual no es la misma")
        }
    }

    private fun changePassword(){
        val url = "http://10.0.2.2:5000/accounts/password/"+intent.getIntExtra("idMemberATE",0).toString()
        HTTPRequest.token = intent.getStringExtra("token").toString()
        val payload = JSONObject()
        payload.put("password", password)
        payload.put("newPassword", newPassword)
        val request = HTTPRequest(
                Request.Method.PATCH,
                url,
                payload,
                Response.Listener { response ->
                    sendMessage("La contraseña se cambio exitosamente")
                    Login.setPassword(newPassword)
                    val accountEdition = Intent(this, AccountEdition::class.java)
                    accountEdition.putExtra("token", intent.getStringExtra("token"))
                    accountEdition.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
                    accountEdition.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
                    accountEdition.putExtra("idCity", intent.getIntExtra("idCity", 0))
                    startActivity(accountEdition)
                    finish()
                },
                requestErrorListener()
        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }

    private fun requestErrorListener(): Response.ErrorListener {
        return Response.ErrorListener { error ->
            Log.e("ERROR", error.networkResponse.toString())
            sendMessage("No hay conexion intente mas tarde")
        }
    }

    private fun sendMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun behindClicked(vew: View){
        val accountEdition = Intent(this, AccountEdition::class.java)
        accountEdition.putExtra("token", intent.getStringExtra("token"))
        accountEdition.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
        accountEdition.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
        accountEdition.putExtra("idCity", intent.getIntExtra("idCity", 0))
        startActivity(accountEdition)
        finish()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectItemNavigation(item)
        return true
    }

    private fun selectItemNavigation(item: MenuItem){
        val fragment: FragmentManager = getSupportFragmentManager()
        val fragmentTransaction: FragmentTransaction = fragment.beginTransaction()
        when(item.itemId) {
            R.id.ItemHome -> {
                val home = Intent(this, Home::class.java)
                home.putExtra("token", intent.getStringExtra("token"))
                home.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
                home.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
                home.putExtra("idCity", intent.getIntExtra("idCity", 0))
                startActivity(home)
                finish()
            }
            R.id.ItemLogout -> {
                val login = Intent(this, Login::class.java)
                startActivity(login)
                finish()
            }
            R.id.ItemEditAccount -> {
                val accountEdition = Intent(this, AccountEdition::class.java)
                accountEdition.putExtra("token", intent.getStringExtra("token"))
                accountEdition.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
                accountEdition.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
                accountEdition.putExtra("idCity", intent.getIntExtra("idCity", 0))
                startActivity(accountEdition)
                finish()
            }
            R.id.ItemRequests -> {
                val requestList = Intent(this, RequestList::class.java)
                requestList.putExtra("token", intent.getStringExtra("token"))
                requestList.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
                requestList.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
                requestList.putExtra("idCity", intent.getIntExtra("idCity", 0))
                startActivity(requestList)
                finish()
            }
            R.id.ItemActiviteAccount -> {
                val activeAccount = Intent(this, ActiveAccount::class.java)
                activeAccount.putExtra("token", intent.getStringExtra("token"))
                activeAccount.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
                activeAccount.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
                activeAccount.putExtra("idCity", intent.getIntExtra("idCity", 0))
                startActivity(activeAccount)
                finish()
            }
            R.id.ItemCommet -> {
                val login = Intent(this, Login::class.java)
                startActivity(login)
                finish()
            }
        }
    }
}