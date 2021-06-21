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
import com.example.trabajosexpres.Model.Service
import com.example.trabajosexpres.Validator.ServiceValidator
import com.example.trabajosexpres.Volley.VolleySingleton
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject

class ServiceAddition: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: Toolbar
    var service = null
    var idMemberATE = 0

    companion object {
        lateinit var serviceAdd: Service
        fun getService(): Service { return serviceAdd }
        fun setService(service: Service) { serviceAdd = service }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.service_addition)
        drawerLayout = findViewById(R.id.drawer_layout_add_service)
        navigationView = findViewById(R.id.NavegationViewUser)
        toolbar = findViewById(R.id.toolbar_employee)
        toolbar.title = "!Bienvenido Usuario "+Login.getUsername()+" !"
        setSupportActionBar(toolbar)
        navigationView.setNavigationItemSelectedListener(this)
    }

    fun behindClicked(vew: View){
        if(intent.getStringExtra("memberATEType").toString().equals("2")){
            val home = Intent(this, HomeEmployee::class.java)
            startActivity(home)
            finish()
        }
    }

    fun registerServiceClicked(view : View) {
        val url = "http://10.0.2.2:5000/services"
        val  serviceToAdd = createDataService()
        val validatorService: Validator<Service> = ServiceValidator()
        val  result: ValidationResult = validatorService.validate(serviceToAdd);
        if(result.isValid){
            HTTPRequest.token = intent.getStringExtra("token").toString()
            val payload = JSONObject()
            payload.put("name", serviceToAdd.name)
            payload.put("description", serviceToAdd.descriptionService)
            payload.put("slogan", serviceToAdd.slogan)
            payload.put("typeService", serviceToAdd.typeService)
            payload.put("minimalCost", serviceToAdd.minimalCost)
            payload.put("maximumCost", serviceToAdd.maximumCost)
            payload.put("idCity", serviceToAdd.idCity)
            payload.put("idMemberATE", serviceToAdd.idMemberATE)
            val request = HTTPRequest(
                Request.Method.POST,
                url,
                payload,
                Response.Listener { response ->
                    serviceAdd =serviceToAdd
                    sendMessage("El servicio se registró exitosamente")
                    val home = Intent(this, HomeEmployee::class.java)
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

    private fun createDataService(): Service {
        val textViewName = findViewById<TextView>(R.id.TextFieldName)
        val textViewDescription = findViewById<TextView>(R.id.TextFieldDescription)
        val textViewSlogan = findViewById<TextView>(R.id.TextFieldSlogan)
        val textViewTypeService = findViewById<TextView>(R.id.TextFieldType)
        val textViewMinimalCost = findViewById<TextView>(R.id.TextFieldInitialCost)
        val textViewMaximumCost = findViewById<TextView>(R.id.TextFieldFinalCost)
        val textViewWorkingHours = findViewById<TextView>(R.id.TextFieldWorkingHours)
        val idCity: Int = 1
        val name: String = textViewName.text.toString()
        val descriptionService: String = textViewDescription.text.toString()
        val slogan: String = textViewSlogan.text.toString()
        val typeService: String = textViewTypeService.text.toString()
        val minimalCost: String = textViewMinimalCost.text.toString()
        val minimalCostInt = minimalCost.toDouble()
        val maximumCost: String = textViewMaximumCost.text.toString()
        val maximumCostInt = maximumCost.toDouble()
        val workingHours: String = textViewWorkingHours.text.toString()
        return Service(
            0,
            serviceAdd.idCity,
            serviceAdd.idMemberATE,
            name,
            minimalCostInt,
            maximumCostInt,
            descriptionService,
            slogan,
            typeService,
            workingHours,
            serviceAdd.serviceStatus
        )
    }

    private fun requestErrorListener(): Response.ErrorListener {
        return Response.ErrorListener { error ->
            Log.e("ERROR", error.networkResponse.toString())
            sendMessage("No hay conexion intente mas tarde")
        }
    }

    private fun sendMessage(message: String) {
        Toast.makeText(this,  message, Toast.LENGTH_SHORT).show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectItemNavigation(item)
        return true
    }

    private fun selectItemNavigation(item: MenuItem){
        val fragment: FragmentManager = getSupportFragmentManager()
        val fragmentTransaction: FragmentTransaction = fragment.beginTransaction()
        when(item.itemId) {
            R.id.ItemHomeEmployee -> {
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
                login.putExtra("token", intent.getStringExtra("token"))
                login.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
                login.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
                login.putExtra("idCity", intent.getIntExtra("idCity", 0))
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
            R.id.ItemRequestsReceived -> {
                val requestList = Intent(this, RequestList::class.java)
                requestList.putExtra("token", intent.getStringExtra("token"))
                requestList.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
                requestList.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
                requestList.putExtra("idCity", intent.getIntExtra("idCity", 0))
                startActivity(requestList)
                finish()
            }

            R.id.ItemRegisterService -> {
                val registerService = Intent(this, RequestList::class.java)
                registerService.putExtra("token", intent.getStringExtra("token"))
                registerService.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
                registerService.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
                registerService.putExtra("idCity", intent.getIntExtra("idCity", 0))
                startActivity(registerService)
                finish()
            }

        }
    }
}