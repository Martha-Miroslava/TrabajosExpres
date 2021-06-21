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
import com.android.volley.Response
import com.example.trabajosexpres.Adpater.AdapterRequest
import com.example.trabajosexpres.HTTPRequest.HTTPRequest
import com.example.trabajosexpres.Model.RequestSent
import com.example.trabajosexpres.Volley.VolleySingleton
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject

class RequestList: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var listRequest: MutableList<RequestSent> = mutableListOf()
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: Toolbar
    companion object {
        lateinit var request: RequestSent
        fun getRequestSend(): RequestSent { return request }
        fun setRequestSend(requestSend: RequestSent) { request = requestSend }
        var position: Int = 0
        fun getPositionRow(): Int { return position }
        fun setPositionRow(positionRow: Int) { position = positionRow }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.requests_list)
        getRequest()
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.NavegationViewUser)
        toolbar = findViewById(R.id.toolbarClient)
        toolbar.title = "!Bienvenido Usuario "+Login.getUsername()+" !"
        setSupportActionBar(toolbar)
        navigationView.setNavigationItemSelectedListener(this)

    }

    fun getRequest() {
        val url = "http://10.0.2.2:5000/requests/1/"+intent.getIntExtra("idMemberATE",0).toString()+"/memberATE"
        HTTPRequest.isArray = true
        HTTPRequest.token = intent.getStringExtra("token").toString()
        val request = HTTPRequest(
                com.android.volley.Request.Method.GET,
                url,
                null,
                requestListener() ,
                requestErrorListener()
        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectItemNavigation(item)
        return true
    }

    private fun requestListener(): Response.Listener<JSONObject>{
        return Response.Listener {
            for (i in 0..it.length()-3){
                val json: JSONObject = it.get(i.toString()) as JSONObject
                val idRequest:Int = json.get("idRequest") as Int
                val idMemberATE: Int = json.get("idMemberATE") as Int
                val address: String = json.get("address") as String
                val date: String= json.get("date") as String
                val time: String= json.get("time") as String
                val trouble: String= json.get("trouble") as String
                val idService: String= json.get("idService") as String
                val requestStatus: Int =  json.get("requestStatus") as Int
                val request:RequestSent = RequestSent(idRequest,address,date,requestStatus,time,trouble,idMemberATE,idService)
                listRequest.add(request)
            }
            val list = findViewById<ListView>(R.id.ListViewRequest)
            list.adapter = AdapterRequest(this,listRequest)
            HTTPRequest.isArray = false
            list.setOnItemClickListener{parent, view, positionRow, id ->
                if(positionRow>=0 && positionRow< listRequest.size){
                    request = listRequest.get(positionRow)
                    position = positionRow
                    val requestClient = Intent(this, RequestClient::class.java)
                    requestClient.putExtra("token", intent.getStringExtra("token"))
                    requestClient.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
                    requestClient.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
                    requestClient.putExtra("idCity", intent.getIntExtra("idCity", 0))
                    startActivity(requestClient)
                    finish()
                }
            }
        }
    }

    private fun requestErrorListener(): Response.ErrorListener {
        return Response.ErrorListener { error ->
            Log.e("ERROR", error.toString())
            HTTPRequest.isArray = false
        }
    }

    private fun selectItemNavigation(item: MenuItem){
        val fragment: FragmentManager = getSupportFragmentManager()
        val fragmentTransaction: FragmentTransaction = fragment.beginTransaction()
        when(item.itemId){
            R.id.ItemHome -> {
                val home = Intent(this, Home::class.java)
                home.putExtra("token", intent.getStringExtra("token"))
                home.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
                home.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
                home.putExtra("idCity", intent.getIntExtra("idCity", 0))
                startActivity(home)
                finish()
            }
            R.id.ItemLogout-> {
                val login = Intent(this, Login::class.java)
                startActivity(login)
                finish()
            }
            R.id.ItemEditAccount ->{
                val accountEdition = Intent(this, AccountEdition::class.java)
                accountEdition.putExtra("token", intent.getStringExtra("token"))
                accountEdition.putExtra("memberATEType", intent.getIntExtra("memberATEType",0))
                accountEdition.putExtra("idMemberATE", intent.getIntExtra("idMemberATE",0))
                accountEdition.putExtra("idCity", intent.getIntExtra("idCity",0))
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