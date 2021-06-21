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
import com.example.trabajosexpres.Adpater.AdapterService
import com.example.trabajosexpres.HTTPRequest.HTTPRequest
import com.example.trabajosexpres.Model.Service
import com.example.trabajosexpres.Volley.VolleySingleton
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject

class HomeEmployee : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
	var listService: MutableList<Service> = mutableListOf()
	lateinit var drawerLayout: DrawerLayout
	lateinit var navigationView: NavigationView
	lateinit var toolbar: Toolbar
	companion object {
		lateinit var service: Service
		fun getServiceMember(): Service { return service }
		fun setServiceMember(newService: Service) { service = newService }
		var position: Int = 0
		fun getPositionRow(): Int { return position }
		fun setPositionRow(positionRow: Int) { position = positionRow }
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.home_employee)
		getService()
		drawerLayout = findViewById(R.id.drawer_layout)
		navigationView = findViewById(R.id.NavegationViewEmployee)
		toolbar = findViewById(R.id.toolbar_employee)
		toolbar.title = "!Bienvenido Usuario "+Login.getUsername()+" !"
		setSupportActionBar(toolbar)
		navigationView.setNavigationItemSelectedListener(this)
	}

	fun getService() {
		val url = "http://10.0.2.2:5000/services/employee/"+intent.getIntExtra("idMemberATE",0).toString()
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

	private fun requestErrorListener(): Response.ErrorListener {
		return Response.ErrorListener { error ->
			Log.e("ERROR", error.toString())
			HTTPRequest.isArray = false
		}
	}

	private fun requestListener(): Response.Listener<JSONObject>{
		return Response.Listener {
			for (i in 0..it.length()-3){
				val json: JSONObject = it.get(i.toString()) as JSONObject
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
			list.setOnItemClickListener{parent, view, positionRow, id ->
				if(positionRow>=0 && positionRow< listService.size){
					Home.service = listService.get(positionRow)
					Home.position = positionRow
					val serviceEmployee = Intent(this, ServiceEmployee::class.java)
					serviceEmployee.putExtra("token", intent.getStringExtra("token"))
					serviceEmployee.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
					serviceEmployee.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
					serviceEmployee.putExtra("idCity", intent.getIntExtra("idCity", 0))
					startActivity(serviceEmployee)
					finish()
				}
			}
		}
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
				val requestListEmployee = Intent(this, RequestListEmployee::class.java)
				requestListEmployee.putExtra("token", intent.getStringExtra("token"))
				requestListEmployee.putExtra("memberATEType", intent.getIntExtra("memberATEType",0))
				requestListEmployee.putExtra("idMemberATE", intent.getIntExtra("idMemberATE",0))
				requestListEmployee.putExtra("idCity", intent.getIntExtra("idCity",0))
				startActivity(requestListEmployee)
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