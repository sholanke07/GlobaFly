package com.lateef.embeddedroomdatabase.data

import androidx.lifecycle.LiveData

class MyRepository(private val myDao: MyDao) {

    //to read data
    val readPerson: LiveData<List<Person>> = myDao.readPerson()

    //to insert preson
    suspend fun insertPerson(person: Person){
        myDao.insertPerson(person)
    }
}