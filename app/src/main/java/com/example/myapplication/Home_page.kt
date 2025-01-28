package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class Home_page : AppCompatActivity(){
    private lateinit var ButtonBack: Button
    private lateinit var ButtonProfile: Button
    private lateinit var ButtonShop: Button
    private lateinit var ButtonBasket: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.home_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ButtonBack = findViewById(R.id.Back)
        ButtonBack.setOnClickListener()
        {
            val intent1 = Intent(this@Home_page,Entrance::class.java)
            startActivity(intent1)
        }
        ButtonProfile = findViewById(R.id.Profile)
        val emaill = intent.getStringExtra("email")
        ButtonProfile.setOnClickListener()
        {
            val intent2 = Intent(this@Home_page,Profile::class.java)
            intent2.putExtra("email",emaill)
            startActivity(intent2)
        }
        ButtonShop = findViewById(R.id.Shop)
        ButtonShop.setOnClickListener()
        {
            val intent3 = Intent(this@Home_page,Shop::class.java)
            startActivity(intent3)
        }
        ButtonBasket = findViewById(R.id.Basket)
        ButtonBasket.setOnClickListener()
        {
            val intent4 = Intent(this@Home_page,Basket::class.java)
            startActivity(intent4)
        }
    }
}