package com.gkprojects.cmmsandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.room.Room
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var AppDb: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout : DrawerLayout = findViewById(R.id.DrawLayout)
        val navView :NavigationView=findViewById(R.id.navView)
        toggle= ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.home_item -> Toast.makeText(applicationContext,"Home Clicked",Toast.LENGTH_SHORT).show()
                R.id.download_item -> Toast.makeText(applicationContext,"Download Clicked",Toast.LENGTH_SHORT).show()
            }
            true
        }


//        AppDb=AppDatabase.getDatabase(this)
//        val db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java, "cmms_db"
//        ).build()
//        val name= "George"
//        val email= "giorgoskouvarakis@gmail.com"
//        val address= "sperheioi16"
//        val date="27-11-2027"
//        val customer= CustomerData(null,name,email,address,date)
//
//        GlobalScope.launch(Dispatchers.IO){
//             AppDb.CustomerDao().insertAll(customer)
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }



    }


