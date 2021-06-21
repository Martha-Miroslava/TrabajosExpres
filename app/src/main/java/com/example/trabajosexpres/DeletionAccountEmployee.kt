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
import com.android.volley.Request
import com.android.volley.Response
import com.example.trabajosexpres.HTTPRequest.HTTPRequest
import com.example.trabajosexpres.Volley.VolleySingleton
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject

class DeletionAccountEmployee: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_deletion)
        drawerLayout = findViewById(R.id.drawer_layout_delete_account)
        navigationView = findViewById(R.id.NavegationViewUser)
        toolbar = findViewById(R.id.toolbar_employee)
        toolbar.title = "!Bienvenido Usuario "+Login.getUsername()+" !"
        setSupportActionBar(toolbar)
        navigationView.setNavigationItemSelectedListener(this)
        initializateAccount()
    }

    fun initializateAccount(){
        val textViewName =findViewById<TextView>(R.id.TextFieldName)
        val textViewLastName =findViewById<TextView>(R.id.TextFieldLastName)
        val textViewDateBith =findViewById<TextView>(R.id.TextFieldDateBirth)
        val textViewEmail =findViewById<TextView>(R.id.TextFieldEmail)
        val textViewCity =findViewById<TextView>(R.id.TextFieldCity)
        val textViewState =findViewById<TextView>(R.id.TextFieldState)
        val textViewUserName =findViewById<TextView>(R.id.TextFieldUserName)
        val textViewPasword =findViewById<TextView>(R.id.TextFieldPassword)

        textViewName.text = AccountEdition.memberATE.name
        textViewLastName.text = AccountEdition.memberATE.lastName
        textViewDateBith.text = AccountEdition.memberATE.dateBirth
        textViewEmail.text = AccountEdition.memberATE.email
        textViewUserName.text = AccountEdition.memberATE.username
        textViewPasword.text = Login.getPassword()
        textViewState.text = "Veracruz"
        textViewCity.text = "Xalapa"
    }

    fun deleteClicked(vew: View){
        val url = "http://10.0.2.2:5000/accounts/"+intent.getIntExtra("idMemberATE",0).toString()
        HTTPRequest.token = intent.getStringExtra("token").toString()
        val payload = JSONObject()
        payload.put("memberATEStatus", 2.toString())
        val request = HTTPRequest(
            Request.Method.PATCH,
            url,
            payload,
            Response.Listener { response ->
                sendMessage("La cuenta se eliminó exitosamente")
                val login = Intent(this, Login::class.java)
                startActivity(login)
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
        val accountEdition = Intent(this, AccountEditionEmployee::class.java)
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

    private fun selectItemNavigation(item: MenuItem) {
        val fragment: FragmentManager = getSupportFragmentManager()
        val fragmentTransaction: FragmentTransaction = fragment.beginTransaction()
        when (item.itemId) {
            R.id.ItemLogout -> {
                val login = Intent(this, Login::class.java)
                startActivity(login)
                finish()
            }
            R.id.ItemHomeEmployee -> {
                val home = Intent(this, HomeEmployee::class.java)
                home.putExtra("token", intent.getStringExtra("token"))
                home.putExtra("memberATEType", intent.getIntExtra("memberATEType",0))
                home.putExtra("idMemberATE", intent.getIntExtra("idMemberATE",0))
                home.putExtra("idCity", intent.getIntExtra("idCity",0))
                startActivity(home)
                finish()
            }
            R.id.ItemRequestsReceived -> {
                val requestEmployee = Intent(this, RequestListEmployee::class.java)
                requestEmployee.putExtra("token", intent.getStringExtra("token"))
                requestEmployee.putExtra("memberATEType", intent.getIntExtra("memberATEType",0))
                requestEmployee.putExtra("idMemberATE", intent.getIntExtra("idMemberATE",0))
                requestEmployee.putExtra("idCity", intent.getIntExtra("idCity",0))
                startActivity(requestEmployee)
                finish()
            }
            R.id.ItemRegisterService -> {
                val serviceAddition = Intent(this, ServiceAddition::class.java)
                serviceAddition.putExtra("token", intent.getStringExtra("token"))
                serviceAddition.putExtra("memberATEType", intent.getIntExtra("memberATEType",0))
                serviceAddition.putExtra("idMemberATE", intent.getIntExtra("idMemberATE",0))
                serviceAddition.putExtra("idCity", intent.getIntExtra("idCity",0))
                startActivity(serviceAddition)
                finish()
            }
            R.id.ItemEditAccount -> {
                val accountEditionEmployee = Intent(this, AccountEditionEmployee::class.java)
                accountEditionEmployee.putExtra("token", intent.getStringExtra("token"))
                accountEditionEmployee.putExtra("memberATEType", intent.getIntExtra("memberATEType",0))
                accountEditionEmployee.putExtra("idMemberATE", intent.getIntExtra("idMemberATE",0))
                accountEditionEmployee.putExtra("idCity", intent.getIntExtra("idCity",0))
                startActivity(accountEditionEmployee)
                finish()
            }
        }
    }
}