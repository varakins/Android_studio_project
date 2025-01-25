package com.example.myapplication
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class Shop : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private val items = arrayOf("Item 1", "Item 2", "Item 3", "Item 4")

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = findViewById(R.id.spinner)

        // Создание адаптера для отображения данных в Spinner
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, items
        )

        // Установка выпадающего вида для адаптера
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Установка адаптера для Spinner
        spinner.adapter = adapter
    }
}