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
    private lateinit var BattonBack: Button
    private lateinit var BattonProfile: Button
    private lateinit var BattonShop: Button
    private lateinit var BattonBasket: Button

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
        BattonBack = findViewById(R.id.Back)
        BattonBack.setOnClickListener()
        {
            val intent1 = Intent(this@Home_page,Entrance::class.java)
            startActivity(intent1)
        }
        BattonProfile = findViewById(R.id.Profile)
        BattonProfile.setOnClickListener()
        {
            val intent2 = Intent(this@Home_page,Profile::class.java)
            startActivity(intent2)
        }
        BattonShop = findViewById(R.id.Shop)
        BattonShop.setOnClickListener()
        {
            val intent3 = Intent(this@Home_page,Shop::class.java)
            startActivity(intent3)
        }
        BattonBasket = findViewById(R.id.Basket)
        BattonBasket.setOnClickListener()
        {
            val intent4 = Intent(this@Home_page,Basket::class.java)
            startActivity(intent4)
        }
    }
}