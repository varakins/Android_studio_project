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
abstract class AppDatabases : RoomDatabase() {
    abstract fun categoryDao(): CategoriesDAO
    abstract fun productDao(): ProductsDAO
    abstract fun userDao(): UsersDAO
    abstract fun orderDao(): OrdersDAO
    abstract fun orderDetailDao(): OrderDetailsDAO


    companion object {
        @Volatile
        private var INSTANCE: AppDatabases? = null

        fun getDatabase(context: Context): AppDatabases {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabases::class.java,
                    "app_databases"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
