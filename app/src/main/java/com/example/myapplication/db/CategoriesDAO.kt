package com.example.myapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Database
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDAO {
    @Insert
    suspend fun insert(category: Categories)

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Categories>>

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    fun getCategoryById(categoryId: Int) : Flow<Categories>

}
