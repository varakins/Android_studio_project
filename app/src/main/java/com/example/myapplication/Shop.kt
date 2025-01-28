package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.db.Products
import com.example.myapplication.R
import java.util.Locale

class Shop : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var productList: ArrayList<Products>
    private lateinit var ButtonBack: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop)

        searchView = findViewById(R.id.searchView)
        recyclerView = findViewById(R.id.recyclerView)
        productList = arrayListOf()
        productList.add(Products(1,"Product 1", "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8cHJvZHVjdHxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=60",10000.00,10,1))
        productList.add(Products(2,"Product 2", "https://images.unsplash.com/photo-1517975882740-25a88521b033?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8cHJvZHVjdHxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=60",15000.00,5,1))
        productList.add(Products(3,"Product 3", "https://images.unsplash.com/photo-1504703369713-19994e36d741?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTF8fHByb2R1Y3R8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=60",20000.00,40,1))
        productList.add(Products(4,"Product 4", "https://images.unsplash.com/photo-1585366594108-789747857414?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTd8fHByb2R1Y3R8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=60",5000.00,0,2))
        productList.add(Products(5,"Product 5", "https://images.unsplash.com/photo-1615267518284-43f198dc6c88?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MjB8fHByb2R1Y3R8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=60",7499.99,50,2))
        adapter = ProductAdapter(productList)
        recyclerView.adapter = adapter
        ButtonBack = findViewById(R.id.Back)

        ButtonBack.setOnClickListener(){
            val intent = Intent(this@Shop,Home_page::class.java)
            startActivity(intent)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

    }
    private fun filterList(query : String?){
        if(query != null){
            val filteredList = ArrayList<Products>()
            for(product in productList){
                if(product.name.lowercase(Locale.ROOT).contains(query)){
                    filteredList.add(product)
                }
            }
            if(filteredList.isEmpty()){
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            }
            adapter.updateData(filteredList)
        }
    }
}