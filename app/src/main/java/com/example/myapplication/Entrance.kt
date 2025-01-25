package com.example.myapplication
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class Entrance : AppCompatActivity(){
    private lateinit var button_recover_password: Button
    private lateinit var button_registration: Button
    private lateinit var button_signIn: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.entrance)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        button_recover_password = findViewById(R.id.recover)
        button_registration = findViewById(R.id.registration)
        button_signIn = findViewById(R.id.signIn)
        button_recover_password.setOnClickListener(){
            val intent1 = Intent(this@Entrance,Recover_password::class.java)
            startActivity(intent1)
        }
        button_registration.setOnClickListener(){
            val intent2 = Intent(this@Entrance,Registration_window::class.java)
            startActivity(intent2)
        }
        button_signIn.setOnClickListener(){
            val intent3 = Intent(this@Entrance,Home_page::class.java)
            startActivity(intent3)
        }
    }
}