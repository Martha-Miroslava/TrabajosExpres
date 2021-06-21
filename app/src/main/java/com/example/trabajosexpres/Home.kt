package com.example.trabajosexpres

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.android.volley.Request
import com.android.volley.Response
import com.example.trabajosexpres.Adpater.AdapterService
import com.example.trabajosexpres.HTTPRequest.HTTPRequest
import com.example.trabajosexpres.Model.Service
import com.example.trabajosexpres.Volley.VolleySingleton
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject

class Home: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var listService: MutableList<Service> = mutableListOf()
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar:Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        getService()
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.NavegationViewUser)
        toolbar = findViewById(R.id.toolbarClient)
        toolbar.title = "!Bienvenido Usuario "+Login.getUsername()+" !"
        setSupportActionBar(toolbar)
        navigationView.setNavigationItemSelectedListener(this)
    }



    fun getService() {
        val url = "http://10.0.2.2:5000/services/city/1"
        HTTPRequest.isArray = true
        HTTPRequest.token = intent.getStringExtra("token").toString()
        val request = HTTPRequest(
                Request.Method.GET,
                url,
                null,
                requestListener() ,
                requestErrorListener()
        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }

    private fun requestListener(): Response.Listener<JSONObject>{
        return Response.Listener {
            for (i in 0..it.length()-3){
                //println(it.get(i.toString()))
                val json:JSONObject = it.get(i.toString()) as JSONObject
                val idService:Int = json.get("idService") as Int
                val idCity:Int = json.get("idCity") as Int
                val idMemberATE: Int = json.get("idMemberATE") as Int
                val name: kotlin.String = json.get("name") as String
                val minimalCost: Double = json.get("minimalCost") as Double
                val maximumCost: Double = json.get("maximumCost") as Double
                val descriptionService: String= json.get("description") as String
                val slogan: kotlin.String= json.get("slogan") as String
                val typeService: kotlin.String= json.get("typeService") as String
                val workingHours: kotlin.String= json.get("workingHours") as String
                val serviceStatus: Int =  json.get("serviceStatus") as Int
                val service:Service = Service(idService, idCity,idMemberATE,name,minimalCost,maximumCost,descriptionService,
                slogan,typeService,workingHours,serviceStatus)
                listService.add(service)
            }
            val list = findViewById<ListView>(R.id.ListViewService)
            list.adapter = AdapterService(this,listService)
            HTTPRequest.isArray = false
        }
    }

    private fun requestErrorListener(): Response.ErrorListener {
        return Response.ErrorListener { error ->
            Log.e("ERROR", error.toString())
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectItemNavigation(item)
        return true
    }

    private fun selectItemNavigation(item: MenuItem){
        val fragment:FragmentManager = getSupportFragmentManager()
        val fragmentTransaction: FragmentTransaction = fragment.beginTransaction()
        when(item.itemId){
            R.id.ItemLogout-> {
                val login = Intent(this, Login::class.java)
                startActivity(login)
                finish()
            }
            R.id.ItemEditAccount ->{
                val accountEdition = Intent(this, AccountEdition::class.java)
                startActivity(accountEdition)
                finish()
            }
            R.id.ItemRequests ->{
                val requestClient = Intent(this, RequestClient::class.java)
                startActivity(requestClient)
                finish()
            }
            R.id.ItemActiviteAccount ->{
                val login = Intent(this, Login::class.java)
                startActivity(login)
                finish()
            }
            R.id.ItemCommet ->{
                val login = Intent(this, Login::class.java)
                startActivity(login)
                finish()
            }
        }
    }
}


