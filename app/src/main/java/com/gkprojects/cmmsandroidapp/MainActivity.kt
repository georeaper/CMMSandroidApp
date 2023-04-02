package com.gkprojects.cmmsandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "cmms_db"
        ).build()
        val name= "George"
        val email= "giorgoskouvarakis@gmail.com"
        val address= "sperheioi16"
        val date="27-11-2027"
        val customer= CustomerData(1,name,email,address,date)

        GlobalScope.launch(Dispatchers.IO){

        }

//        val customerDao = db.userDao()
//        //val customer = CustomerDao.insertAll()

    }


}