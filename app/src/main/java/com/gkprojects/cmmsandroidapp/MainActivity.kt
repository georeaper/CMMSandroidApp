package com.gkprojects.cmmsandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.gkprojects.cmmsandroidapp.Fragments.*
import com.gkprojects.cmmsandroidapp.Fragments.Contracts.ContractFragment
import com.google.android.material.appbar.MaterialToolbar

import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById<DrawerLayout>(R.id.DrawLayout)
        val navView: NavigationView = findViewById(R.id.navView)
        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
//        val titleCustomer = R.

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        setSupportActionBar(toolbar)


        val startFragment = supportFragmentManager
        val firstTransactionFrag =startFragment.beginTransaction()
        firstTransactionFrag.replace(R.id.frameLayout1, HomeFragment())
        firstTransactionFrag.commit()
        drawerLayout.closeDrawers()
        toolbar.title= "Home"

        //setTitle(this.title.toString())


        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            it.isChecked=true
            when(it.itemId){
                R.id.home_item -> replaceFragment(HomeFragment(),it.title.toString())
                R.id.customer_item -> replaceFragment(CustomerFragment(),it.title.toString())
                R.id.equipment_item -> replaceFragment(EquipmentFragment(),it.title.toString())
                R.id.workOrder_item ->replaceFragment(Work_Orders(),it.title.toString())
                R.id.cases_item -> replaceFragment(CasesFragment(),it.title.toString())
                R.id.contract_item -> replaceFragment(ContractFragment(),it.title.toString())
                R.id.settings_item -> replaceFragment(SettingsFragment(),it.title.toString())
 }
            true
        }



        }
private fun replaceFragment(fragment : Fragment , title :String){

    val fragmentManager =supportFragmentManager
    val fragmentTransaction=fragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.frameLayout1,fragment)
    fragmentTransaction.commit()
    drawerLayout.closeDrawers()
    val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
    toolbar.title = title

}
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }




    }


