package com.example.myapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Database
import kotlinx.coroutines.flow.Flow
import androidx.room.Update

@Dao
interface UsersDAO {
    @Insert
    suspend fun insert(user: Users)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    fun getUserByEmailAndPassword(email: String, password: String) : Flow<Users?>

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String) : Flow<Users>
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Int) : Flow<Users>
    @Query("SELECT * FROM users")
    fun getAllUsers() : Flow<List<Users>>

    @Update
    suspend fun update(user: Users)

}
