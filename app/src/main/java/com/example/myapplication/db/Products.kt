package com.example.myapplication.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = Categories::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["categoryId"])]
)
data class Products(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val image: ByteArray? = null, // Image as ByteArray
    val price: Double,
    val qty: Int,
    val categoryId: Int
)

