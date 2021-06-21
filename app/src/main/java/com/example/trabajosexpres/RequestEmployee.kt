package com.example.trabajosexpres

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject

class RequestEmployee: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_employee)

        initializateRequest()
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.NavegationViewUser)
        toolbar = findViewById(R.id.toolbar_employee)
        toolbar.title = "!Bienvenido Usuario "+Login.getUsername()+" !"
        setSupportActionBar(toolbar)
        navigationView.setNavigationItemSelectedListener(this)
    }

    fun cancelClicked (vew: View){
        MaterialAlertDialogBuilder(this)
            .setTitle("Cancelación de solicitud")
            .setMessage("¿Seguro desea cancelar la solicitud?")
            .setNegativeButton("No") { dialog, which ->
            }
            .setPositiveButton("Sí") { dialog, which ->
                val url = "http://10.0.2.2:5000/requests/"+RequestListEmployee.request.idRequest.toString()
                HTTPRequest.token = intent.getStringExtra("token").toString()
                val payload = JSONObject()
                payload.put("requestStatus", 4.toString())
                val request = HTTPRequest(
                    Request.Method.PATCH,
                    url,
                    payload,
                    Response.Listener { response ->
                        sendMessage("La solicitud se cancelo exitosamente")
                        val requestListEmployee = Intent(this, RequestListEmployee::class.java)
                        requestListEmployee.putExtra("token", intent.getStringExtra("token"))
                        requestListEmployee.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
                        requestListEmployee.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
                        requestListEmployee.putExtra("idCity", intent.getIntExtra("idCity", 0))
                        startActivity(requestListEmployee)
                        finish()
                    },
                    requestErrorListener()
                )
                VolleySingleton.getInstance(this).addToRequestQueue(request)
            }
            .show()
    }

    fun initializateRequest(){
        val textViewName =findViewById<TextView>(R.id.TextFieldName)
        val textViewAddress =findViewById<TextView>(R.id.TextFieldAddress)
        val textViewDate =findViewById<TextView>(R.id.TextFieldDate)
        val textViewHour =findViewById<TextView>(R.id.TextFieldHour)
        val textViewProblem =findViewById<TextView>(R.id.TextFieldProblem)
        val image = findViewById<ImageView>(R.id.ImageViewRequest)
        val numberIndex:Int = RequestList.position%2
        if(numberIndex!=0){
            image.setImageResource(R.drawable.carpintero_1)
        }else{
            image.setImageResource(R.drawable.plomero)
        }
        textViewName.text = RequestListEmployee.request.idMemberATE
        textViewAddress.text = RequestListEmployee.request.address
        textViewDate.text = RequestListEmployee.request.date
        textViewHour.text = RequestListEmployee.request.time
        textViewProblem.text = RequestListEmployee.request.trouble
    }

    private fun requestErrorListener(): Response.ErrorListener {
        return Response.ErrorListener { error ->
            Log.e("ERROR", error.toString())
            sendMessage("No se pudo cancelar la solicitud. Intente más tarde")
        }
    }

    private fun sendMessage(message: String) {
        Toast.makeText(this,  message, Toast.LENGTH_SHORT).show()
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
                login.putExtra("token", intent.getStringExtra("token"))
                login.putExtra("memberATEType", intent.getIntExtra("memberATEType",0))
                login.putExtra("idMemberATE", intent.getIntExtra("idMemberATE",0))
                login.putExtra("idCity", intent.getIntExtra("idCity",0))
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