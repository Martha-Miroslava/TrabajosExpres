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

class RequestClient: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_client)
        initializateRequest()
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.NavegationViewUser)
        toolbar = findViewById(R.id.toolbarClient)
        toolbar.title = "!Bienvenido Usuario "+Login.getUsername()+" !"
        setSupportActionBar(toolbar)
        navigationView.setNavigationItemSelectedListener(this)
    }

    fun initializateRequest(){
        val textViewName =findViewById<TextView>(R.id.TextFieldName)
        val textViewAddress =findViewById<TextView>(R.id.TextFieldAddress)
        val textViewDate =findViewById<TextView>(R.id.TextFieldDate)
        val textViewHour =findViewById<TextView>(R.id.TextFieldHour)
        val textViewProble =findViewById<TextView>(R.id.TextFieldProblem)
        val image = findViewById<ImageView>(R.id.ImageViewRequest)
        val numberIndex:Int = RequestList.position%2
        if(numberIndex!=0){
            image.setImageResource(R.drawable.carpintero_1)
        }else{
            image.setImageResource(R.drawable.plomero)
        }
        textViewName.text = RequestList.request.idService
        textViewAddress.text = RequestList.request.address
        textViewDate.text = RequestList.request.date
        textViewHour.text = RequestList.request.time
        textViewProble.text = RequestList.request.trouble
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectItemNavigation(item)
        return true
    }

    fun CancelClicked (vew: View){
        MaterialAlertDialogBuilder(this)
            .setTitle("Cancelación de solicitud")
            .setMessage("¿Seguro desea cancelar la solicitud?")
            .setNegativeButton("No") { dialog, which ->
            }
            .setPositiveButton("Sí") { dialog, which ->
                val url = "http://10.0.2.2:5000/requests/"+RequestList.request.idRequest.toString()
                HTTPRequest.token = intent.getStringExtra("token").toString()
                val payload = JSONObject()
                payload.put("requestStatus", 4.toString())
                val request = HTTPRequest(
                    Request.Method.PATCH,
                    url,
                    payload,
                    Response.Listener { response ->
                        sendMessage("La solicitud se cancelo exitosamente")
                        val requestList = Intent(this, RequestList::class.java)
                        requestList.putExtra("token", intent.getStringExtra("token"))
                        requestList.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
                        requestList.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
                        requestList.putExtra("idCity", intent.getIntExtra("idCity", 0))
                        startActivity(requestList)
                        finish()
                    },
                    requestErrorListener()
                )
                VolleySingleton.getInstance(this).addToRequestQueue(request)
            }
            .show()
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