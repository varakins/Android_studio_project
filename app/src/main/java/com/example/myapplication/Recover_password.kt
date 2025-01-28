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
import com.example.myapplication.db.AppDatabases
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.core.widget.doAfterTextChanged


class Recover_password : AppCompatActivity(){
    private lateinit var ddb: AppDatabases
    private lateinit var userDao: UsersDAO
    private lateinit var ButtonBack: Button
    private lateinit var ButtonRecover: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.recover_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ddb = AppDatabases.getDatabase(applicationContext)
        userDao = ddb.userDao()
        ButtonBack = findViewById(R.id.Back)
        ButtonRecover = findViewById(R.id.Regist)
        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)

        emailEditText.text.clear()
        passwordEditText.text.clear()

        ButtonRecover.isEnabled = false
        emailEditText.doAfterTextChanged {checkFields() }
        passwordEditText.doAfterTextChanged{ checkFields()}



        ButtonBack.setOnClickListener(){
            val intent = Intent(this@Recover_password,Entrance::class.java)
            startActivity(intent)
        }

        ButtonRecover.setOnClickListener(){
            lifecycleScope.launch {
                changePassword()
            }
        }


    }

    fun checkFields(){
        ButtonRecover.isEnabled = emailEditText.text.isNotBlank()
                && passwordEditText.text.isNotBlank()
    }

    private suspend fun changePassword() {
        val email = emailEditText.text.toString()
        val newPassword = passwordEditText.text.toString()

        userDao.getUserByEmail(email).collect {
            user -> if(user != null){
                val updatedUser = user.copy(password = newPassword)
                userDao.update(updatedUser)
                Toast.makeText(this@Recover_password, "Password Updated!", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this@Recover_password, "User not found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}