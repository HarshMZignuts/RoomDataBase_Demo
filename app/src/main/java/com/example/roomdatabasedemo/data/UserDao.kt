package com.example.roomdatabasedemo.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomdatabasedemo.model.User
import java.util.concurrent.Flow

//User dao interface is seconed created
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user : User)

    @Update
    suspend fun updateUser(user:User )

    @Delete
    suspend fun deleteUser(user : User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()


    @Query("SELECT * FROM user_table ORDER BY id ASC ")
    fun readAllData(): LiveData<List<User>>

//   @Query("SELECT * FROM user_table WHERE firstName LIKE '%' || :searchQuery || '%' ORDER BY id ASC" )
//   fun searchData(searchQuery : String) : LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE firstName LIKE :searchQuery OR lastName LIKE :searchQuery")

    fun searchDatabase(searchQuery: String): LiveData<List<User>>
}