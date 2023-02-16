package com.lateef.embeddedroomdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyDao {

    //for inserting data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: Person)


    //for reading all data
    @Query("SELECT * FROM my_table ORDER BY id ASC")
    fun readPerson(): LiveData<List<Person>>
}