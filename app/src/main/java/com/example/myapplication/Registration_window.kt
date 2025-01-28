package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.db.Users
import com.example.myapplication.db.UsersDAO
import androidx.core.widget.doAfterTextChanged
import com.example.myapplication.db.AppDatabases
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class Registration_window : AppCompatActivity(){

    private lateinit var ddb: AppDatabases
    private lateinit var userDao: UsersDAO
    private lateinit var ButtonBack: Button
    private lateinit var ButtonRegistration: Button
    private lateinit var lastNameEditText:EditText
    private lateinit var firstNameEditText:EditText
    private lateinit var middleNameEditText:EditText
    private lateinit var phoneEditText:EditText
    private lateinit var emailEditText:EditText
    private lateinit var passwordEditText:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registration_window)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ddb = AppDatabases.getDatabase(applicationContext)
        userDao = ddb.userDao()
        ButtonBack = findViewById(R.id.Back)
        ButtonRegistration = findViewById(R.id.Regist)
        lastNameEditText = findViewById(R.id.editTextLastName)
        firstNameEditText = findViewById(R.id.editTextFirstName)
        middleNameEditText = findViewById(R.id.editTextMiddleName)
        phoneEditText = findViewById(R.id.editTextPhone)
        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)

        lastNameEditText.text.clear()
        firstNameEditText.text.clear()
        middleNameEditText.text.clear()
        phoneEditText.text.clear()
        emailEditText.text.clear()
        passwordEditText.text.clear()

        ButtonRegistration.isEnabled = false
        lastNameEditText.doAfterTextChanged { checkFields() }
        firstNameEditText.doAfterTextChanged { checkFields() }
        middleNameEditText.doAfterTextChanged { checkFields() }
        phoneEditText.doAfterTextChanged { checkFields() }
        emailEditText.doAfterTextChanged { checkFields() }
        passwordEditText.doAfterTextChanged { checkFields() }

        ButtonBack.setOnClickListener(){
            val intent = Intent(this@Registration_window,Entrance::class.java)
            startActivity(intent)
        }
        ButtonRegistration.setOnClickListener(){
            lifecycleScope.launch {
                insertUser()
            }
        }
    }

    fun checkFields(){
        ButtonRegistration.isEnabled = lastNameEditText.text.isNotBlank()
                && firstNameEditText.text.isNotBlank()
                && middleNameEditText.text.isNotBlank()
                && phoneEditText.text.isNotBlank()
                && emailEditText.text.isNotBlank()
                && passwordEditText.text.isNotBlank()

    }

    private suspend fun insertUser() {


        val newUser = Users(
            lastName = lastNameEditText.text.toString(),
            firstName = firstNameEditText.text.toString(),
            middleName = middleNameEditText.text.toString(),
            phone = phoneEditText.text.toString(),
            email = emailEditText.text.toString(),
            password = passwordEditText.text.toString()
        )

        userDao.insert(newUser)

        Toast.makeText(this@Registration_window, "User added", Toast.LENGTH_SHORT).show()

        lastNameEditText.text.clear()
        firstNameEditText.text.clear()
        middleNameEditText.text.clear()
        phoneEditText.text.clear()
        emailEditText.text.clear()
        passwordEditText.text.clear()

    }
}