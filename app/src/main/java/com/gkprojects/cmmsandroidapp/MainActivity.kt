package com.gkprojects.cmmsandroidapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.gkprojects.cmmsandroidapp.Fragments.*
import com.gkprojects.cmmsandroidapp.Fragments.Contracts.ContractFragment
import com.gkprojects.cmmsandroidapp.Fragments.Contracts.ContractInsertFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

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
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

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

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    // Handle Home click
                    // You can add your logic here
                    true
                }
                R.id.action_search -> {
                    // Handle Search click
                    // You can add your logic here
                    true
                }
                R.id.action_popUpMenu -> {
                    // Handle Add click
                    showCustomDialog(this)
                    // You can add your logic here
                    true
                }
                R.id.action_settings -> {
                    // Open the custom dialog when Settings is clicked

                    true
                }
                R.id.action_notification -> {
                    // Handle Notifications click
                    // You can add your logic here
                    true
                }
                else -> false
            }
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

    fun showCustomDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_open_create_methods, null)
        builder.setView(dialogView)
        var dialog: AlertDialog? = null // Declare dialog as nullable

        val option1Button: Button = dialogView.findViewById(R.id.option1Button)
        val option2Button: Button = dialogView.findViewById(R.id.option2Button)
        val option3Button: Button = dialogView.findViewById(R.id.option3Button)
        val option4Button: Button = dialogView.findViewById(R.id.option4Button)
        val option5Button: Button = dialogView.findViewById(R.id.option5Button)

        option1Button.setOnClickListener {
            // Handle Option 1 click
            val fragmentManager =supportFragmentManager
            val transaction=fragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout1, EditCustomerFragment())
            transaction.commit()
            dialog?.dismiss()
        }

        option2Button.setOnClickListener {
            // Handle Option 2 click
            val fragmentManager =supportFragmentManager
            val transaction=fragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout1, ContractInsertFragment())
            transaction.addToBackStack(null)
            transaction.commit()
            dialog?.dismiss()
        }

        option3Button.setOnClickListener {
            // Handle Option 3 click
            val fragmentManager =supportFragmentManager
            val transaction=fragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout1, EquipmentInsertFragment())
            transaction.commit()
            dialog?.dismiss()
        }

        option4Button.setOnClickListener {
            // Handle Cancel click
            val fragmentManager =supportFragmentManager
            val transaction=fragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout1, CaseInsertFragment())
            transaction.commit()
            dialog?.dismiss()
        }
        option5Button.setOnClickListener {
            // Handle Cancel click
            val fragmentManager =supportFragmentManager
            val transaction=fragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout1, CaseInsertFragment())
            transaction.commit()
            dialog?.dismiss()
        }

        dialog = builder.create()
        dialog.show()
    }


    }


