package com.example.myapplication.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "orders",
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])]
)
data class Orders(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val orderDate: Date,
    val totalSum: Double,
    val status: OrderStatus,
    val userId: Int
)

enum class OrderStatus {
    PENDING, PROCESSING, SHIPPED, DELIVERED
}

