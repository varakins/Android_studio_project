package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.db.Products
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Basket : AppCompatActivity()  {
    private lateinit var ButtonBack: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.basket)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ButtonBack = findViewById(R.id.Back)
        ButtonBack.setOnClickListener()
        {
            val intent = Intent(this@Basket,Home_page::class.java)
            startActivity(intent)
        }

        val sharedPreferences = this.getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val basket = getBasketFromSharedPreferences(sharedPreferences)
        val basketTextView: TextView = findViewById(R.id.basketTextView) // Replace with your TextView ID

        basketTextView.text = basket.joinToString("\n") { it.name ?: "" }
    }

    private fun getBasketFromSharedPreferences(sharedPreferences: SharedPreferences): List<Products> {
        val gson = Gson()
        val jsonBasket = sharedPreferences.getString(ProductAdapter.BASKET_KEY, "[]") ?: "[]"
        val listType: Type = object : TypeToken<List<Products>>() {}.type
        return gson.fromJson(jsonBasket, listType)
    }


}
