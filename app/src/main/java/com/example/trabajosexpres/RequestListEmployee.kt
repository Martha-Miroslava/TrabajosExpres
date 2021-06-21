package com.example.trabajosexpres

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.android.volley.Request
import com.android.volley.Response
import com.example.trabajosexpres.Adpater.AdapterRequestReceived
import com.example.trabajosexpres.HTTPRequest.HTTPRequest
import com.example.trabajosexpres.Model.RequestReceived
import com.example.trabajosexpres.Model.Service
import com.example.trabajosexpres.Volley.VolleySingleton
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject

class RequestListEmployee: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    var listService: MutableList<Service> = mutableListOf()
    var listRequest: MutableList<RequestReceived> = mutableListOf()
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: Toolbar
    companion object {
        lateinit var request: RequestReceived
        fun getRequestReceived(): RequestReceived { return request }
        fun setRequestReceived(requestReceived: RequestReceived) { request = requestReceived }
        var position: Int = 0
        fun getPositionRow(): Int { return position }
        fun setPositionRow(positionRow: Int) { position = positionRow }
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_list_employee)
        getRequest()
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.NavegationViewUser)
        toolbar = findViewById(R.id.toolbar_employee)
        toolbar.title = "!Bienvenido Usuario "+Login.getUsername()+" !"
        setSupportActionBar(toolbar)
        navigationView.setNavigationItemSelectedListener(this)
    }


    fun getRequest() {
        val url = "http://10.0.2.2:5000/requests/1/"+intent.getIntExtra("idMemberATE",0).toString()+"/service"
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
                val json: JSONObject = it.get(i.toString()) as JSONObject
                val idRequest:Int = json.get("idRequest") as Int
                val idMemberATE: String = json.get("idMemberATE") as String
                val address: String = json.get("address") as String
                val date: String= json.get("date") as String
                val time: String= json.get("time") as String
                val trouble: String= json.get("trouble") as String
                val idService: Int= json.get("idService") as Int
                val requestStatus: Int =  json.get("requestStatus") as Int
                val request:RequestReceived = RequestReceived(idRequest,address,date,requestStatus,time,trouble,idMemberATE,idService)
                listRequest.add(request)
            }
            val list = findViewById<ListView>(R.id.ListViewRequestReceived)
            list.adapter = AdapterRequestReceived(this,listRequest)
            list.setOnItemClickListener{parent, view, positionRow, id ->
                if(positionRow>=0 && positionRow< listRequest.size){
                    RequestListEmployee.request = listRequest.get(positionRow)
                    RequestListEmployee.position = positionRow
                    if(listRequest.get(positionRow).requestStatus== 1){
                        val requestFirstEmployee = Intent(this, RequestFirstEmployee::class.java)
                        requestFirstEmployee.putExtra("token", intent.getStringExtra("token"))
                        requestFirstEmployee.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
                        requestFirstEmployee.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
                        requestFirstEmployee.putExtra("idCity", intent.getIntExtra("idCity", 0))
                        startActivity(requestFirstEmployee)
                        finish()
                    }else{
                        val requestEmployee = Intent(this, RequestEmployee::class.java)
                        requestEmployee.putExtra("token", intent.getStringExtra("token"))
                        requestEmployee.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
                        requestEmployee.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
                        requestEmployee.putExtra("idCity", intent.getIntExtra("idCity", 0))
                        startActivity(requestEmployee)
                        finish()
                    }
                }
            }
            HTTPRequest.isArray = false
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectItemNavigation(item)
        return true
    }

    private fun requestErrorListener(): Response.ErrorListener {
        return Response.ErrorListener { error ->
            Log.e("ERROR", error.toString())
            HTTPRequest.isArray = false
        }
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