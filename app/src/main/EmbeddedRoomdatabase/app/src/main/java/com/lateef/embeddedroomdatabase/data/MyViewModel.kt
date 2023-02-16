package com.lateef.embeddedroomdatabase.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.file.LinkPermission

class MyViewModel(application: Application): AndroidViewModel(application) {

    val readPerson: LiveData<List<Person>>
    private val repository: MyRepository
    
    init {
        val myDao = MyDatabase.getDatabase(application).myDao()
        repository = MyRepository(myDao)
        readPerson = repository.readPerson
    }



    fun insertPerson(person: Person){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertPerson(person)

        }

    }
}



    

