package com.example.myapplication.db

import androidx.room.TypeConverter

class OrderStatusConverter {
    @TypeConverter
    fun fromStatus(value: OrderStatus): String {
        return value.name
    }

    @TypeConverter
    fun toStatus(value: String): OrderStatus {
        return OrderStatus.valueOf(value)
    }
}
