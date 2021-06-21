package com.example.trabajosexpres

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView

class HomeEmployee : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
	lateinit var drawerLayout: DrawerLayout
	lateinit var navigationView: NavigationView
	lateinit var toolbar: Toolbar
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.home_employee)
		drawerLayout = findViewById(R.id.drawer_layout)
		navigationView = findViewById(R.id.NavegationViewEmployee)
		toolbar = findViewById(R.id.toolbar_employee)
		toolbar.title = "!Bienvenido Usuario "+Login.getUsername()+" !"
		setSupportActionBar(toolbar)
		navigationView.setNavigationItemSelectedListener(this)
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
				val home = Intent(this, Home::class.java)
				startActivity(home)
				finish()
			}
			R.id.ItemHomeEmployee -> {
				val home = Intent(this, HomeEmployee::class.java)
				startActivity(home)
				finish()
			}
			R.id.ItemRequestsReceived -> {
				val requestEmployee = Intent(this, RequestEmployee::class.java)
				startActivity(requestEmployee)
				finish()
			}
			R.id.ItemRegisterService -> {
				val serviceAddition = Intent(this, ServiceAddition::class.java)
				startActivity(serviceAddition)
				finish()
			}
			R.id.ItemEditAccount -> {
				val accountEdition = Intent(this, AccountEdition::class.java)
				accountEdition.putExtra("token", intent.getStringExtra("token"))
				accountEdition.putExtra("memberATEType", intent.getIntExtra("memberATEType",0))
				accountEdition.putExtra("idMemberATE", intent.getIntExtra("idMemberATE",0))
				accountEdition.putExtra("idCity", intent.getIntExtra("idCity",0))
				startActivity(accountEdition)
				finish()
			}
		}
	}
}