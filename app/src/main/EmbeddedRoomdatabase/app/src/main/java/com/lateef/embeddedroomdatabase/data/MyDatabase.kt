package com.lateef.embeddedroomdatabase.data

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities= [Person::class], version = 1, exportSchema = false)
abstract class MyDatabase: RoomDatabase() {

    abstract fun myDao(): MyDao

    companion object{
        @Volatile  // means things in this field are made visible to other threads
        private var INSTANCE: MyDatabase?= null

        //in there we are checking if the instance is not 0
        fun getDatabase(context: Context): MyDatabase =
             INSTANCE ?: synchronized(this) {
                INSTANCE ?:
                buildDatabase(context).also {
                    INSTANCE = it}

            }
        private fun buildDatabase(context: Context) =
                 Room.databaseBuilder(
                    context.applicationContext,     MyDatabase::class.java, "my_database"
                ).build()
        
    }
}