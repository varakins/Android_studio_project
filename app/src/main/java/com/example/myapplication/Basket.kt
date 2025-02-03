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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.withContext
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import android.widget.Toast

class Basket : AppCompatActivity() {

    private lateinit var basketRecyclerView: RecyclerView
    private lateinit var totalPriceTextView: TextView
    private lateinit var buyButton: Button
    private lateinit var adapter: BasketAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var ButtonBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basket)

        basketRecyclerView = findViewById(R.id.basketRecyclerView)
        totalPriceTextView = findViewById(R.id.totalPriceTextView)
        buyButton = findViewById(R.id.buyButton)
        ButtonBack = findViewById(R.id.Back)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Загрузка данных из SharedPreferences
        val basket = getBasket(sharedPreferences)
        adapter = BasketAdapter(basket)
        basketRecyclerView.adapter = adapter
        basketRecyclerView.layoutManager = LinearLayoutManager(this)

        // Расчет общей стоимости
        val totalPrice = basket.sumOf { it.product.price * it.quantity }
        totalPriceTextView.text = String.format("Общая стоимость: %.2f Р", totalPrice)

        // Обработка нажатия на кнопку "Купить"
        buyButton.setOnClickListener {
            Toast.makeText(this, "Покупка завершена!", Toast.LENGTH_SHORT).show()
            // Очистка корзины после покупки
            sharedPreferences.edit().remove(ProductAdapter.BASKET_KEY).apply()
            finish()
        }
        ButtonBack.setOnClickListener(){
            val intent = Intent(this@Basket,Home_page::class.java)
            startActivity(intent)
        }
    }

    private fun getBasket(sharedPreferences: SharedPreferences): List<BasketItem> {
        val gson = Gson()
        val jsonBasket = sharedPreferences.getString(ProductAdapter.BASKET_KEY, "[]") ?: "[]"
        val listType: Type = object : TypeToken<List<BasketItem>>() {}.type
        return gson.fromJson(jsonBasket, listType)
    }
}
