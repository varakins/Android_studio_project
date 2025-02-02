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
import kotlinx.coroutines.flow.take
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

    private fun isValidPhone(phone: String): Boolean {
        val phoneRegex = Regex("^89\\d{9}$") // Начинается с 89 и содержит 11 цифр
        return phoneRegex.matches(phone)
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[^@]+@[^@]+\\.[^@]+$") // Содержит @ и не начинается с него
        return emailRegex.matches(email)
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    fun checkFields() {
        val phone = phoneEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        val isPhoneValid = isValidPhone(phone)
        val isEmailValid = isValidEmail(email)
        val isPasswordValid = isValidPassword(password)

        // Отображение ошибок
        if (phone.isNotBlank() && !isPhoneValid) {
            phoneEditText.error = "Телефон должен состоять из 11 цифр и начинаться с 89"
        } else {
            phoneEditText.error = null
        }

        if (email.isNotBlank() && !isEmailValid) {
            emailEditText.error = "Некорректный email"
        } else {
            emailEditText.error = null
        }

        if (password.isNotBlank() && !isPasswordValid) {
            passwordEditText.error = "Пароль должен быть не менее 8 символов"
        } else {
            passwordEditText.error = null
        }

        // Активация кнопки
        ButtonRegistration.isEnabled = lastNameEditText.text.isNotBlank() &&
                firstNameEditText.text.isNotBlank() &&
                middleNameEditText.text.isNotBlank() &&
                phoneEditText.text.isNotBlank() &&
                emailEditText.text.isNotBlank() &&
                passwordEditText.text.isNotBlank() &&
                isPhoneValid &&
                isEmailValid &&
                isPasswordValid
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

        userDao.getUserByEmail(emailEditText.text.toString()).take(1).collect() {
                user -> if(user != null){
            Toast.makeText(this@Registration_window, "Пользователь с такой электронной почтой уже есть", Toast.LENGTH_SHORT).show()
        }
        else {
            userDao.insert(newUser)

            Toast.makeText(this@Registration_window, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()

            lastNameEditText.text.clear()
            firstNameEditText.text.clear()
            middleNameEditText.text.clear()
            phoneEditText.text.clear()
            emailEditText.text.clear()
            passwordEditText.text.clear()
        }
        }



    }
}