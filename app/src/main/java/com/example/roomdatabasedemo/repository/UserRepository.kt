package com.example.roomdatabasedemo.repository

import androidx.lifecycle.LiveData
import com.example.roomdatabasedemo.data.UserDao
import com.example.roomdatabasedemo.model.User

//UserRepository is created 4th

class UserRepository(private val userDao: UserDao) {

    val readAllData : LiveData<List<User>> = userDao.readAllData()

   suspend fun addUser(user: User)
    {
        userDao.addUser(user)
    }
     suspend fun updateUser(user : User)
     {
         userDao.updateUser(user)
     }
    suspend fun deleteUser(user : User)
    {
        userDao.deleteUser(user)
    }
    suspend fun deleteAll()
    {
        userDao.deleteAll()
    }
//    fun searchData(searchQuery : String) : LiveData<List<User>>{
//        return userDao.searchData(searchQuery)
//    }
    fun searchDatabase(searchQuery: String): LiveData<List<User>> {
        return userDao.searchDatabase(searchQuery)
    }
}