package com.example.myapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Database
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDAO {
    @Insert
    suspend fun insert(product: Products)

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Products>>

    @Query("SELECT * FROM products WHERE categoryId = :categoryId")
    fun getAllProductsInCategory(categoryId: Int) : Flow<List<Products>>

    @Query("SELECT * FROM products WHERE id = :productId")
    fun getProductById(productId: Int) : Flow<Products>
}
