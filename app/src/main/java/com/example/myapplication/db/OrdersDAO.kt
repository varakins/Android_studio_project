package com.example.myapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Database
import kotlinx.coroutines.flow.Flow

@Dao
interface OrdersDAO {
    @Insert
    suspend fun insert(order: Orders)
    @Query("SELECT * FROM orders WHERE userId = :userId")
    fun getAllOrdersByUser(userId: Int) : Flow<List<Orders>>

}
