package com.gkprojects.cmmsandroidapp.api.SyncData

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.Models.CustomerVM
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DataSynchronizer(private val context: Context) {
    private val customerViewModel : CustomerVM = ViewModelProvider(context as AppCompatActivity)[CustomerVM::class.java]
    private val syncDataModel : SyncDataViewModel = ViewModelProvider(context as AppCompatActivity)[SyncDataViewModel::class.java]

    fun synchronize() {
        var customerList = listOf<Customer>()

        // Fetch data
        customerViewModel.getAllCustomerData(context).observe(context as LifecycleOwner) { customers ->
            customerList = customers

            // Sync data
            val myFormat = "yyyy-MM-dd HH:mm:ss" // your format
            val sdf = SimpleDateFormat(myFormat, Locale.ROOT)

            val date = Date() // current date
            val timestamp = sdf.format(date)
            val deviceId = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
                .getString("device_id", null)

            syncDataModel.authenticate(context, deviceId!!, timestamp, customerList)
        }

        // Handle sync state
        syncDataModel.authResponse.observe(context as LifecycleOwner) { syncState ->
            when (syncState) {
                is com.gkprojects.cmmsandroidapp.api.SyncData.AuthState.Failure -> {
                    val message = syncState.message
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }

                com.gkprojects.cmmsandroidapp.api.SyncData.AuthState.Loading -> {
                    // Show loading indicator
                }

                com.gkprojects.cmmsandroidapp.api.SyncData.AuthState.NoConnection -> Toast.makeText(
                    context,
                    "No internet connection",
                    Toast.LENGTH_SHORT
                ).show()

                is com.gkprojects.cmmsandroidapp.api.SyncData.AuthState.Success -> {
                    val response = syncState.response
                    Log.d("SyncSuccess", "$response")
                    syncANDmatch(response)
                }

                null -> {
                    // Do nothing
                }
            }
        }
    }
    private fun syncANDmatch(list :List<Customer>){
        customerViewModel.getAllCustomerData(context).observe(context as LifecycleOwner, Observer { localCustomers ->
            for (remoteCustomer in list) {
                if(localCustomers.any { it.CustomerID == remoteCustomer.CustomerID }) {
                    // If remoteCustomer.CustomerID is in localCustomers then update
                    customerViewModel.updateSync(context, remoteCustomer)
                } else {
                    // Else remoteCustomer.CustomerID is not in localCustomers then insert
                    customerViewModel.insertSync(context, remoteCustomer)
                }
            }
        })
    }
}