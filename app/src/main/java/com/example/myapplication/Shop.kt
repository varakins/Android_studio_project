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
        productList.add(Products(1,"Обои виниловые Adawall Tropikano 1.06x15.6 м цвет бежевый", "https://cdn.lemanapro.ru/lmru/image/upload/f_auto/q_auto/dpr_1.0/c_pad/w_1000/h_1000/v1723371316/lmcode/ZdENZWEZw0GNOaA8h5fhBg/93710557.jpg",3920.00,60,1))
        productList.add(Products(2,"Стеновая пластиковая панель ЗАРЯ-2000 2700х250х8 мм белая", "https://cdn.stroylandiya.ru/upload/iblock/1b6/12d1uoqspogin005z02j9knb15mv32z9.jpg",275.00,75,2))
        productList.add(Products(3,"Стеновая пластиковая панель VILLAGIO Камень 1 344 2700х250х8 мм коричневая", "https://cdn.stroylandiya.ru/upload/iblock/d24/d24bef877ab576d283b2a9d2c3fa2063.jpg",400.00,80,2))
        productList.add(Products(4,"Плинтус потолочный СОЛИД 2000х21х25 мм", "https://cdn.stroylandiya.ru/upload/iblock/b45/b456fd72b8176a800ac7bbc00e0dce35.jpg",25.00,100,3))
        productList.add(Products(5,"Ограждение для душа VILLAGIO 80х80 см матовое стекло SW-805S", "https://cdn.stroylandiya.ru/upload/iblock/e57/o3er5kpp5wvlel1ylj1y9j139wpg8sqr.jpg",7499.99,50,4))
        productList.add(Products(6,"Люстра светодиодная VILLAGIO 19547/40 LED 72 Вт", "https://cdn.stroylandiya.ru/upload/iblock/e59/evpxkn15s3oqsyzg4n90b9t9jur1riwi.jpg",3520.00,16,5))
        productList.add(Products(7,"Люстра потолочная RIVOLI Constancia 9081-303 3хЕ27х40 Вт белая", "https://cdn.stroylandiya.ru/upload/iblock/464/46441bb8f7ef93283d951f0ff43755e3.jpg",5235.00,21,5))
        productList.add(Products(8,"Светодиодная лампа ОНЛАЙТ G45 Е27 220 В 6 Вт матовый шар 4000 К холодный свет", "https://cdn.stroylandiya.ru/upload/iblock/9a5/3i0n8wd22vt37exep5xxrnx31bn86uxv.jpg",110.00,80,5))
        productList.add(Products(9,"Ламинат 7 мм/32 класс KRONOSTAR Eco-Tec 1380x193 мм дуб сердания 2080", "https://cdn.stroylandiya.ru/upload/iblock/450/57e8li486lipj83jnx6o10t2x07sufzi.jpg",1890.22,20,6))
        productList.add(Products(10,"Ламинат 8 мм/33 класс WOODSTYLE Bravo 1291х193 мм дуб хайберг с фаской", "https://cdn.stroylandiya.ru/upload/iblock/17c/4k61ffmlfmuke4ox49fjdqm8pcx4tv3t.jpg",2000.00,50,6))
        productList.add(Products(11,"Плитка керамическая AXIMA Наварра низ 037190 20х30 см серая", "https://cdn.stroylandiya.ru/upload/iblock/05d/d1w1utm0cl17ycs7c3yz2m4fed6o73lx.jpg",27.00,100,7))
        productList.add(Products(12,"Плитка керамическая AXIMA Лигурия верх 037091 20х30 см бежевая", "https://cdn.stroylandiya.ru/upload/iblock/c23/c233ac7b66aec3034f87fb5ea6f16c81.jpg",34.70,500,7))
        productList.add(Products(13,"Плитка керамическая AXIMA Каталония 027962 40х40 см дуб беленый", "https://cdn.stroylandiya.ru/upload/iblock/e9e/45jk96tcvljtgrxetuaosfcg9dny6oom.jpg",130.00,400,7))
        productList.add(Products(14,"Окно пластиковое ПВХ REHAU одностворчатое 600х900 мм правое поворотное однокамерный стеклопакет", "https://cdn.stroylandiya.ru/upload/iblock/e7d/e7d1a97ecf6e683b4fefcf4dffbbc6f7.jpg",4653.00,15,8))
        productList.add(Products(15,"Дверь межкомнатная СТРОЙТЕХ Эко-тек КЛ-7 800х2000 мм лиственница белая", "https://cdn.stroylandiya.ru/upload/iblock/dc3/sblu8fna4jzl2ja0y0l5iux7s150rlhl.jpg",5499.99,10,9))
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