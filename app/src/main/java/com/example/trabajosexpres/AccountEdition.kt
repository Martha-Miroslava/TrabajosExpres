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
import com.example.trabajosexpres.Model.MemberATE
import com.example.trabajosexpres.Validator.MemberATEValidator
import com.example.trabajosexpres.Volley.VolleySingleton
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject

class AccountEdition: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: Toolbar

    companion object {
        lateinit var memberATE:MemberATE
        fun getMember(): MemberATE { return memberATE }
        fun setMember(member: MemberATE) { memberATE = member }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_edition)
        drawerLayout = findViewById(R.id.drawer_layout_edition_account)
        navigationView = findViewById(R.id.NavegationViewUser)
        toolbar = findViewById(R.id.toolbarClient)
        toolbar.title = "!Bienvenido Usuario "+Login.getUsername()+" !"
        setSupportActionBar(toolbar)
        navigationView.setNavigationItemSelectedListener(this)
        getAccount()
    }

    fun behindClicked(vew: View){
        if(intent.getStringExtra("memberATEType").toString().equals("2")){
            val home = Intent(this, HomeEmployee::class.java)
            startActivity(home)
            finish()
        }else{
            val home = Intent(this, Home::class.java)
            startActivity(home)
            finish()
        }
    }

    fun deleteClicked(vew: View){
        val accountDeletion = Intent(this, AccountDeletion::class.java)
        accountDeletion.putExtra("token", intent.getStringExtra("token"))
        accountDeletion.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
        accountDeletion.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
        accountDeletion.putExtra("idCity", intent.getIntExtra("idCity", 0))
        startActivity(accountDeletion)
        finish()
    }

    fun chagePasswordClicked(vew: View){
        val passwordChange = Intent(this, PasswordChange::class.java)
        passwordChange.putExtra("token", intent.getStringExtra("token"))
        passwordChange.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
        passwordChange.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
        passwordChange.putExtra("idCity", intent.getIntExtra("idCity", 0))
        startActivity(passwordChange)
        finish()
    }

    fun getAccount(){
        val url = "http://10.0.2.2:5000/accounts/"+intent.getIntExtra("idMemberATE",0).toString()
        HTTPRequest.token = intent.getStringExtra("token").toString()
        val request = HTTPRequest(
                Request.Method.GET,
                url,
                null,
                responseListener(),
                requestErrorListener()
        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }

    fun updtaeAccount(vew: View){
        val url = "http://10.0.2.2:5000/accounts/"+intent.getIntExtra("idMemberATE",0).toString()
        val  memberATEEdit = createDataAccount()
        val validatorAccount: Validator<MemberATE> = MemberATEValidator()
        val  result: ValidationResult = validatorAccount.validate(memberATEEdit);
        if(result.isValid) {
            HTTPRequest.token = intent.getStringExtra("token").toString()
            val payload = JSONObject()
            payload.put("idAccount", memberATEEdit.id)
            payload.put("username", memberATEEdit.username)
            payload.put("lastname", memberATEEdit.lastName)
            payload.put("name", memberATEEdit.name)
            payload.put("dateBirth", memberATEEdit.dateBirth)
            payload.put("email", memberATEEdit.email)
            payload.put("idCity", memberATEEdit.idCity)
            val request = HTTPRequest(
                    Request.Method.PUT,
                    url,
                    payload,
                    Response.Listener { response ->
                        memberATE=memberATEEdit
                        sendMessage("La cuenta se modifico exitosamente")
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
        }
        else{
            sendMessage(result.errors.elementAt(0).message)
        }
    }

    private fun createDataAccount():MemberATE {
        val textViewName =findViewById<TextView>(R.id.TextFieldName)
        val textViewLastName =findViewById<TextView>(R.id.TextFieldLastName)
        val textViewDateBith =findViewById<TextView>(R.id.TextFieldDateBirth)
        val textViewEmail =findViewById<TextView>(R.id.TextFieldEmail)
        val textViewUserName =findViewById<TextView>(R.id.TextFieldUserName)
        val userName: String = textViewUserName.text.toString()
        val name: String = textViewName.text.toString()
        val lastname: String = textViewLastName.text.toString()
        val email: String = textViewEmail.text.toString()
        val date: String = textViewDateBith.text.toString()
        val memberATEEdit = MemberATE(memberATE.id, email, date, lastname, name, userName, Login.getPassword(), memberATE.accountType,memberATE.idCity, memberATE.memberATEStatus)
        return memberATEEdit
    }

    private fun responseListener(): Response.Listener<JSONObject> {
        return Response.Listener { response ->
            val idAccount:Int = response.get("idAccount") as Int
            val username:String = response.get("username") as String
            val password:String = response.get("password") as String
            val name:String = response.get("name") as String
            val lastname:String = response.get("lastname") as String
            val dateBirth:String = response.get("dateBirth") as String
            val email:String = response.get("email") as String
            val idCity:Int = response.get("idCity") as Int
            val memberATEType:Int = response.get("memberATEType") as Int
            val memberATEStatus:Int = response.get("memberATEStatus") as Int
            memberATE = MemberATE(idAccount,email,dateBirth,lastname,name,username,password,memberATEType ,idCity,memberATEStatus)
            initializateAccount()
        }
    }

    private  fun initializateAccount(){
        val textViewName =findViewById<TextView>(R.id.TextFieldName)
        val textViewLastName =findViewById<TextView>(R.id.TextFieldLastName)
        val textViewDateBith =findViewById<TextView>(R.id.TextFieldDateBirth)
        val textViewEmail =findViewById<TextView>(R.id.TextFieldEmail)
        val textViewUserName =findViewById<TextView>(R.id.TextFieldUserName)
        textViewName.text = memberATE.name
        textViewLastName.text =memberATE.lastName
        textViewDateBith.text = memberATE.dateBirth
        textViewEmail.text = memberATE.email
        textViewUserName.text = memberATE.username
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
            R.id.ItemActiviteAccount -> {
                val activeAccount = Intent(this, ActiveAccount::class.java)
                activeAccount.putExtra("token", intent.getStringExtra("token"))
                activeAccount.putExtra("memberATEType", intent.getIntExtra("memberATEType", 0))
                activeAccount.putExtra("idMemberATE", intent.getIntExtra("idMemberATE", 0))
                activeAccount.putExtra("idCity", intent.getIntExtra("idCity", 0))
                startActivity(activeAccount)
                finish()
            }
            R.id.ItemCommet -> {
                val login = Intent(this, Login::class.java)
                startActivity(login)
                finish()
            }
        }
    }
}