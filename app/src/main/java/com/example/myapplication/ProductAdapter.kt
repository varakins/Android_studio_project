package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.db.Products
import android.content.Intent
import kotlinx.coroutines.withContext
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import android.widget.Toast


class ProductAdapter(productList: List<Products>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // Объявляем productList как var внутри класса
    private var productList: List<Products> = productList

    companion object {
        const val BASKET_KEY = "basket"
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val addToBasketButton: Button = itemView.findViewById(R.id.ButtonBasket)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productName.text = product.name
        holder.productPrice.text = String.format("%.2f Р", product.price)
        Glide.with(holder.productImage.context)
            .load(product.imageURL)
            .placeholder(R.drawable.hdun)
            .error(R.drawable.image_error)
            .into(holder.productImage)
        holder.addToBasketButton.setOnClickListener {
            addProductToBasket(holder.itemView.context, product)
        }
    }

    private fun addProductToBasket(context: Context, product: Products) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val basket: MutableList<BasketItem> = getBasket(sharedPreferences)

        // Проверяем, есть ли уже такой товар в корзине
        val existingItemIndex = basket.indexOfFirst { it.product.id == product.id }
        if (existingItemIndex != -1) {
            // If item exists, copy existingItem and change the quantity
            val existingItem = basket[existingItemIndex]
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            basket[existingItemIndex] = updatedItem

        } else {
            // Если товара нет, добавляем его в корзину
            basket.add(BasketItem(product, 1))
        }

        // Сохраняем обновленную корзину в SharedPreferences
        val jsonBasket = gson.toJson(basket)
        sharedPreferences.edit().putString(BASKET_KEY, jsonBasket).apply()

        // Показываем уведомление
        Toast.makeText(context, "${product.name} добавлен в корзину", Toast.LENGTH_SHORT).show()

        // Переход в активность Basket (опционально)
        val intent = Intent(context, Basket::class.java)
        context.startActivity(intent)
    }

    private fun getBasket(sharedPreferences: SharedPreferences): MutableList<BasketItem> {
        val gson = Gson()
        val jsonBasket = sharedPreferences.getString(BASKET_KEY, "[]") ?: "[]"
        val listType: Type = object : TypeToken<MutableList<BasketItem>>() {}.type
        return gson.fromJson(jsonBasket, listType)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    // Обновление данных в адаптере
    fun updateData(newList: List<Products>) {
        productList = newList // Теперь это работает, так как productList объявлен как var
        notifyDataSetChanged()
    }
}

data class BasketItem(val product: Products, val quantity: Int)