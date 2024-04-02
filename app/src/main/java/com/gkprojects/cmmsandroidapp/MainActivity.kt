package com.gkprojects.cmmsandroidapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.Fragments.*
import com.gkprojects.cmmsandroidapp.Fragments.Configuration.ConfigurationFragment
import com.gkprojects.cmmsandroidapp.Fragments.Contracts.ContractFragment
import com.gkprojects.cmmsandroidapp.Fragments.Contracts.ContractInsertFragment
import com.gkprojects.cmmsandroidapp.Fragments.Customers.CustomerFragment
import com.gkprojects.cmmsandroidapp.Fragments.Customers.EditCustomerFragment
import com.gkprojects.cmmsandroidapp.Fragments.Equipments.EquipmentFragment
import com.gkprojects.cmmsandroidapp.Fragments.Equipments.EquipmentInsertFragment
import com.gkprojects.cmmsandroidapp.Fragments.Inventory.InventoryFragment
import com.gkprojects.cmmsandroidapp.Fragments.Settings.SettingsFragment
import com.gkprojects.cmmsandroidapp.Fragments.SpecialTools.SpecialTools
import com.gkprojects.cmmsandroidapp.Fragments.TechnicalCases.CaseInsertFragment
import com.gkprojects.cmmsandroidapp.Fragments.TechnicalCases.CasesFragment
import com.gkprojects.cmmsandroidapp.Fragments.WorkOrders.Work_Orders
import com.gkprojects.cmmsandroidapp.Models.CustomerVM
import com.gkprojects.cmmsandroidapp.api.ApiViewModel
import com.gkprojects.cmmsandroidapp.api.SyncData.DataSynchronizer
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG_HOME = "Home"
        const val TAG_CUSTOMER = "Customer"
        const val TAG_EQUIPMENTS = "Equipments"
        const val TAG_CASES = "Technical Cases"
        const val TAG_CONTRACTS = "Contracts"
        const val TAG_SETTINGS = "Settings"
        const val TAG_WORK_ORDERS = "Work Orders"
        const val TAG_CONTRACT_INSERT = "Edit Contract"
        const val TAG_EQUIPMENT_INSERT = "Edit Equipment"
        const val TAG_CUSTOMER_INSERT = "Edit Customer"
        const val TAG_CUSTOMER_DASHBOARD = "Dashboard Customer"
        const val TAG_CONFIGURATION = "Configuration"
        const val TAG_STATISTICS = "Statistics"
        const val TAG_TOOLS = "Tools"
        const val TAG_INVENTORY = "Inventory"


    }
    private lateinit var viewModel: CustomerVM
//    private lateinit var viewModelApi : ApiViewModel
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private var currentFragmentTag = "Home"
    private var isReadPermissionGranted =false
    private var isWritePermissionGranted =false
    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        isReadPermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted
        isWritePermissionGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: isWritePermissionGranted
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()
        drawerLayout = findViewById<DrawerLayout>(R.id.DrawLayout)
        val navView: NavigationView = findViewById(R.id.navView)
        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        setSupportActionBar(toolbar)
        //Syncrhonization Dialog
        showSynchronizationDialog()

        val startFragment = supportFragmentManager
        val firstTransactionFrag =startFragment.beginTransaction()
        firstTransactionFrag.replace(R.id.frameLayout1, HomeFragment())
        firstTransactionFrag.commit()
        drawerLayout.closeDrawers()
        toolbar.title= "Home"
        badgeCreation()
//        viewModelApi = ViewModelProvider(this).get(ApiViewModel::class.java)

//        testApi()

        navView.setNavigationItemSelectedListener {
            it.isChecked=true
            when(it.itemId){
                R.id.home_item -> replaceFragment(HomeFragment(), TAG_HOME)
                R.id.customer_item -> replaceFragment(CustomerFragment(), TAG_CUSTOMER)
                R.id.equipment_item -> replaceFragment(EquipmentFragment(), TAG_EQUIPMENTS)
                R.id.workOrder_item ->replaceFragment(Work_Orders(), TAG_WORK_ORDERS)
                R.id.cases_item -> replaceFragment(CasesFragment(), TAG_CASES)
                R.id.contract_item -> replaceFragment(ContractFragment(), TAG_CONTRACTS)
//                R.id.settings_item -> replaceFragment(SettingsFragment(), TAG_SETTINGS)
                R.id.tools_item ->replaceFragment(SpecialTools(), TAG_TOOLS)
                R.id.inventory_item ->replaceFragment(InventoryFragment(), TAG_INVENTORY)
                R.id.configuration_item -> replaceFragment(ConfigurationFragment(), TAG_CONFIGURATION)
                R.id.statistics_item -> replaceFragment(StatisticsFragment(), TAG_STATISTICS)
 }
            true
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    if (currentFragmentTag != TAG_HOME) {

                        replaceFragment(HomeFragment(), TAG_HOME)
                    }
                    true
                }

//                R.id.action_popUpMenu -> {
//                    // Handle Add click
//                    showCustomDialog(this)
//                    // You can add your logic here
//                    true
//                }
                R.id.action_settings -> {
                    if (currentFragmentTag != TAG_SETTINGS) {
                        replaceFragment(SettingsFragment(), TAG_SETTINGS)
                    }

                    true
                }
                R.id.action_notification -> {
                    val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
                    val badge = bottomNavigationView.getOrCreateBadge(R.id.action_notification)
                    badge.isVisible = false
// An optional step, you can display the number of notifications on the badge
                    badge.clearNumber()
                    // Handle Notifications click
                    // You can add your logic here
                    true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }

        }



    }
    private fun showSynchronizationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Synchronization")
            .setMessage("Do you want to synchronize data?")
            .setPositiveButton("Yes") { dialog, which ->
                DataSynchronizer(this).synchronize()
            }
            .setNegativeButton("No", null)
            .show()
    }


    private fun updateTest(customer: Customer) {
        Log.d("testInActivity2","$customer")
        viewModel.updateCustomer(this,customer)

    }
//    private fun testApi(){
//
//
//        viewModelApi.authResponse.observe(this, Observer { response ->
//            // Update UI with the response
//        })
//
//        viewModelApi.authenticate()
//    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout1, fragment, tag)
            .commit()
        currentFragmentTag = tag // Keep track of the current fragment
        drawerLayout.closeDrawers()
        val toolbar : MaterialToolbar = findViewById(R.id.topAppBar)
        toolbar.title = tag // Optional: Set the toolbar title to the tag
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
            transaction.replace(R.id.frameLayout1, Work_Orders())
            transaction.commit()
            dialog?.dismiss()
        }

        dialog = builder.create()
        dialog.show()
    }

    private fun requestPermission(){

        isReadPermissionGranted= ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )== PackageManager.PERMISSION_GRANTED


        isWritePermissionGranted= ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )== PackageManager.PERMISSION_GRANTED

        val permissionRequest :MutableList<String> = ArrayList()

        if(!isReadPermissionGranted){
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if(!isWritePermissionGranted){
            permissionRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if(permissionRequest.isNotEmpty()){

            permissionLauncher.launch(permissionRequest.toTypedArray())
        }

    }

        private fun badgeCreation(){
            val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
            val badge = bottomNavigationView.getOrCreateBadge(R.id.action_notification)
            badge.isVisible = true
// An optional step, you can display the number of notifications on the badge
            badge.number = 15
        }
    }


