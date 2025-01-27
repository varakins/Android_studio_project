package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.db.Users
import com.example.myapplication.db.UsersDAO
import kotlinx.coroutines.launch
import androidx.core.widget.doAfterTextChanged
import com.example.myapplication.db.AppDatabase
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.core.widget.doAfterTextChanged


class Entrance : AppCompatActivity(){

    private lateinit var ddb: AppDatabase
    private lateinit var userDao: UsersDAO
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var button_recover_password: Button
    private lateinit var button_registration: Button
    private lateinit var button_signIn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.entrance)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ddb = AppDatabase.getDatabase(applicationContext)
        userDao = ddb.userDao()
        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        button_recover_password = findViewById(R.id.recover)
        button_registration = findViewById(R.id.registration)
        button_signIn = findViewById(R.id.signIn)

        emailEditText.text.clear()
        passwordEditText.text.clear()

        button_signIn.isEnabled = false
        emailEditText.doAfterTextChanged {checkFields() }
        passwordEditText.doAfterTextChanged{ checkFields()}

        button_recover_password.setOnClickListener(){
            val intent1 = Intent(this@Entrance,Recover_password::class.java)
            startActivity(intent1)
        }
        button_registration.setOnClickListener(){
            val intent2 = Intent(this@Entrance,Registration_window::class.java)
            startActivity(intent2)
        }
        button_signIn.setOnClickListener(){
            lifecycleScope.launch {
                checkUser()
            }
        }
    }

    fun checkFields(){
        button_signIn.isEnabled = emailEditText.text.isNotBlank()
                && passwordEditText.text.isNotBlank()
    }
    private suspend fun checkUser() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        userDao.getUserByEmailAndPassword(email, password).collect{
                user ->  if(user != null){
            Toast.makeText(this@Entrance, "Login success", Toast.LENGTH_SHORT).show()
            val intent3 = Intent(this@Entrance,Home_page::class.java)
            startActivity(intent3)
        }else{
            Toast.makeText(this@Entrance, "Login fail", Toast.LENGTH_SHORT).show()
        }
        }
    }


}