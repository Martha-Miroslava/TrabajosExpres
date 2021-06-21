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
import com.example.trabajosexpres.Validator.RequestValidator
import com.example.trabajosexpres.Volley.VolleySingleton
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject

class RequestAddition: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_addition)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.NavegationViewUser)
        toolbar = findViewById(R.id.toolbarClient)
        toolbar.title = "!Bienvenido Usuario "+Login.getUsername()+" !"
        setSupportActionBar(toolbar)
        navigationView.setNavigationItemSelectedListener(this)

        val textFieldName =findViewById<TextView>(R.id.TextFieldName)
        textFieldName.text = Home.service.name
    }

    fun registerRequestClicked(view : View) {
        val  request = createDataRequest()
        val validatorRequest: Validator<com.example.trabajosexpres.Model.Request> = RequestValidator()
        val  result: ValidationResult = validatorRequest.validate(request);
        if(result.isValid){
            val url = "http://10.0.2.2:5000/requests"
            HTTPRequest.token = intent.getStringExtra("token").toString()
            val payload = JSONObject()
            payload.put("idRequest", request.idRequest)
            payload.put("address", request.address)
            payload.put("date", request.date)
            payload.put("time", request.time)
            payload.put("trouble", request.trouble)
            payload.put("idMemberATE", request.idMemberATE)
            payload.put("idService", request.idService)
            val request = HTTPRequest(
                Request.Method.POST,
                url,
                payload,
                Response.Listener {
                    sendMessage("La solicitud se registro exitosamente")
                    val home = Intent(this, Home::class.java)
                    home.putExtra("token", intent.getStringExtra("token"))
                    home.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
                    home.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
                    home.putExtra("idCity", intent.getIntExtra("idCity", 0))
                    startActivity(home)
                    finish()
                },
                requestErrorListener()
            )
            VolleySingleton.getInstance(this).addToRequestQueue(request)
        }else{
            sendMessage(result.errors.elementAt(0).message)
        }
    }

    private fun createDataRequest(): com.example.trabajosexpres.Model.Request {
        val textViewAddress =findViewById<TextView>(R.id.TextFieldAddress)
        val textViewDate=findViewById<TextView>(R.id.TextFieldDate)
        val textViewTime =findViewById<TextView>(R.id.TextFieldHour)
        val textViewProblem =findViewById<TextView>(R.id.TextFieldProblem)

        val address: String = textViewAddress.text.toString()
        val date: String = textViewDate.text.toString()
        val time: String = textViewTime.text.toString()
        val problem: String = textViewProblem.text.toString()

        val request = com.example.trabajosexpres.Model.Request(0,address,date,1,time,problem,
        intent.getIntExtra("idMemberATE",0),Home.service.idService)
        return request
    }

    private fun requestErrorListener(): Response.ErrorListener {
        return Response.ErrorListener { error ->
            Log.e("ERROR", error.networkResponse.toString())
            sendMessage("No hay conexion intente mas tarde")
        }
    }

    fun behindClicked(vew: View){
        val home = Intent(this, Home::class.java)
        home.putExtra("token", intent.getStringExtra("token"))
        home.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
        home.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
        home.putExtra("idCity", intent.getIntExtra("idCity", 0))
        startActivity(home)
        finish()
    }

    private fun sendMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
        }
    }
}