package com.example.myapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Database
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDetailsDAO {
    @Insert
    suspend fun insert(orderDetail: OrderDetails)

    @Query("SELECT * FROM order_details WHERE orderId = :orderId")
    fun getOrderDetailsByOrderId(orderId: Int) : Flow<List<OrderDetails>>

}
