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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider



class Profile : AppCompatActivity(){
    private lateinit var ddb: AppDatabases
    private lateinit var userDao: UsersDAO
    private lateinit var ButtonBack: Button
    private lateinit var ButtonUpdate: Button
    private lateinit var lastNameEditText: EditText
    private lateinit var firstNameEditText: EditText
    private lateinit var middleNameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var emailEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ddb = AppDatabases.getDatabase(applicationContext)
        userDao = ddb.userDao()
        lastNameEditText = findViewById(R.id.editTextLastName)
        firstNameEditText = findViewById(R.id.editTextFirstName)
        middleNameEditText = findViewById(R.id.editTextMiddleName)
        phoneEditText = findViewById(R.id.editTextPhone)
        emailEditText = findViewById(R.id.editTextEmail)
        ButtonBack = findViewById(R.id.Back)
        ButtonUpdate = findViewById(R.id.ButtonUpdate)

        ButtonUpdate.isEnabled = false
        lastNameEditText.doAfterTextChanged { checkFields() }
        firstNameEditText.doAfterTextChanged { checkFields() }
        middleNameEditText.doAfterTextChanged { checkFields() }
        phoneEditText.doAfterTextChanged { checkFields() }
        emailEditText.doAfterTextChanged { checkFields() }

        loadUserData()

        if (emailEditText.text.isNullOrEmpty()) {
            val email = intent.getStringExtra("email")
            emailEditText.setText(email)
            lifecycleScope.launch {
                DataUsers()
            }
        }


        emailEditText.isFocusable = false
        emailEditText.isFocusableInTouchMode = false

        ButtonBack.setOnClickListener()
        {
            saveUserData()
            val intent = Intent(this@Profile,Home_page::class.java)
            startActivity(intent)
        }

        ButtonUpdate.setOnClickListener(){
            lifecycleScope.launch {
                UpdateUsers()
            }
        }
    }

    fun checkFields(){
        ButtonUpdate.isEnabled = lastNameEditText.text.isNotBlank()
                && firstNameEditText.text.isNotBlank()
                && middleNameEditText.text.isNotBlank()
                && phoneEditText.text.isNotBlank()
                && emailEditText.text.isNotBlank()

    }

    private fun saveUserData() {
        val sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("lastName", lastNameEditText.text.toString())
        editor.putString("firstName", firstNameEditText.text.toString())
        editor.putString("middleName", middleNameEditText.text.toString())
        editor.putString("phone", phoneEditText.text.toString())
        editor.putString("email", emailEditText.text.toString())
        editor.apply()
    }

    private fun loadUserData() {
        val sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)
        lastNameEditText.setText(sharedPreferences.getString("lastName", ""))
        firstNameEditText.setText(sharedPreferences.getString("firstName", ""))
        middleNameEditText.setText(sharedPreferences.getString("middleName", ""))
        phoneEditText.setText(sharedPreferences.getString("phone", ""))
        emailEditText.setText(sharedPreferences.getString("email", ""))
    }

    private suspend fun DataUsers(){

        combine(
            userDao.getLastNameUserByEmail(emailEditText.text.toString()),
            userDao.getFirstNameUserByEmail(emailEditText.text.toString()),
            userDao.getMiddleNameUserByEmail(emailEditText.text.toString()),
            userDao.getPhoneUserByEmail(emailEditText.text.toString())
        ) { lastName, firstName, middleName, phone ->
            lastNameEditText.setText(lastName ?: "")
            firstNameEditText.setText(firstName ?: "")
            middleNameEditText.setText(middleName ?: "")
            phoneEditText.setText(phone ?: "")
        }.take(1).collect()

    }

    private suspend fun UpdateUsers() {

        userDao.getUserByEmail(emailEditText.text.toString()).take(1).collect {
                user -> if(user != null){
            val updatedUser = user.copy(lastName = lastNameEditText.text.toString(),
                firstName = firstNameEditText.text.toString(),
                middleName = middleNameEditText.text.toString(),
                phone = phoneEditText.text.toString()
            )
            userDao.update(updatedUser)

            Toast.makeText(this@Profile, "Данные изменены", Toast.LENGTH_SHORT).show()
            }
        }



    }
}