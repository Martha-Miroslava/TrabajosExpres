package com.example.trabajosexpres

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView


class ServiceEmployeeConsult: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.service_employee_consult)
        drawerLayout = findViewById(R.id.drawer_layout_employee)
        navigationView = findViewById(R.id.NavegationViewEmployee)
        toolbar = findViewById(R.id.toolbar_employee)
        toolbar.title = "!Bienvenido Usuario "+Login.getUsername()+" !"
        setSupportActionBar(toolbar)
        navigationView.setNavigationItemSelectedListener(this)
        initializateService()
    }

    fun initializateService(){
        val textViewName =findViewById<TextView>(R.id.TextFieldName)
        val textViewSlogan =findViewById<TextView>(R.id.TextFieldSlogan)
        val textViewType =findViewById<TextView>(R.id.TextFieldType)
        val textViewCost =findViewById<TextView>(R.id.TextFieldCost)
        val textViewDescription =findViewById<TextView>(R.id.TextFieldDescription)
        val textViewSchedule =findViewById<TextView>(R.id.TextFieldSchedule)
        val textViewState =findViewById<TextView>(R.id.TextFieldState)
        val textViewCity =findViewById<TextView>(R.id.TextFieldCity)
        val image = findViewById<ImageView>(R.id.ImageViewService)
        val numberIndex:Int = Home.position%2
        if(numberIndex!=0){
            image.setImageResource(R.drawable.carpintero_1)
        }else{
            image.setImageResource(R.drawable.plomero)
        }
        textViewName.text = Home.service.name
        textViewSlogan.text = Home.service.slogan
        textViewType.text = Home.service.typeService
        textViewCost.text = "De: "+Home.service.minimalCost+" Hasta:"+Home.service.maximumCost
        textViewDescription.text = Home.service.descriptionService
        textViewSchedule.text = Home.service.workingHours
        textViewState.text = "Veracruz"
        textViewCity.text = "Xalapa"
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
                val requestEmployee = Intent(this, RequestEmployee::class.java)
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