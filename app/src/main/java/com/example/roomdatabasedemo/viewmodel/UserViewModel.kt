package com.example.roomdatabasedemo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomdatabasedemo.data.UserDao
import com.example.roomdatabasedemo.data.UserDataBase
import com.example.roomdatabasedemo.repository.UserRepository
import com.example.roomdatabasedemo.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

//UserViewModel is created 5th

class UserViewModel(application: Application): AndroidViewModel(application) {
     val readAllData : LiveData<List<User>>
    private val repository : UserRepository
    private val userDao = UserDataBase.getDatabase(application).userDao()

    init {

        repository = UserRepository(userDao)
        readAllData = repository.readAllData

    }

    fun addUser(user: User)
    {
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(user)
        }
    }
    fun updateUser(user : User)
    {
        viewModelScope.launch (Dispatchers.IO){

            repository.updateUser(user)
        }
    }
    fun deleteUser(user : User){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteUser(user)
        }
    }
    fun deleteAll(){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAll()
        }
    }

//    fun searchData(searchQuery : String):LiveData<List<User>>{
//
//        return repository.searchData(searchQuery)
//    }

//    val serchQurry = MutableStateFlow("")
//    private val taskFlow = serchQurry.flatMapLatest {
//        userDao.searchData(it)
//    }
fun searchDatabase(searchQuery: String): LiveData<List<User>> {
    return repository.searchDatabase(searchQuery)
}




}