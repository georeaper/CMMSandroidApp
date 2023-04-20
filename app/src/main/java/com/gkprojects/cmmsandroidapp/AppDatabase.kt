package com.gkprojects.cmmsandroidapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CustomerData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun CustomerDao(): CustomerDao

    companion object {
        @Volatile
        private var INSTANCE :AppDatabase?=null

        fun getDatabase(context : Context):AppDatabase{
            val tempInstance= INSTANCE
            if(tempInstance!=null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"cmms_db").build()
                INSTANCE=instance
                return instance
            }

        }


    }
}