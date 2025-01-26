package com.example.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters



@Database(
    entities = [Categories::class, Products::class, Users::class, Orders::class, OrderDetails::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, OrderStatusConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoriesDAO
    abstract fun productDao(): ProductsDAO
    abstract fun userDao(): UsersDAO
    abstract fun orderDao(): OrdersDAO
    abstract fun orderDetailDao(): OrderDetailsDAO


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
