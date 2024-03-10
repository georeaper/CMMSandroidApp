package com.gkprojects.cmmsandroidapp.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.DashboardCustomerContractsDataClass
import com.gkprojects.cmmsandroidapp.DataClasses.DashboardCustomerEquipmentDataClass
import com.gkprojects.cmmsandroidapp.DataClasses.DashboardCustomerTechnicalCasesDataClass

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoCustomer {
    companion object{
        var userDatabase: CMMSDatabase?=null

        private fun intialiseDB(context: Context): CMMSDatabase?
        {
            return CMMSDatabase.getInstance(context)!!
        }

        fun insert(context: Context, customer : Customer)
        {
            userDatabase= intialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.CustomerDao().addCustomer(customer)
            }
        }
        fun getCustomerID(context :Context ,id :String):LiveData<Customer>{
            userDatabase= intialiseDB(context)
            return userDatabase!!.CustomerDao().getCustomerByID(id)
        }

        fun delete(context: Context,customer: Customer){
            userDatabase= intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.CustomerDao().deleteCustomer(customer)
            }

        }

        fun getAllCustomerData(context: Context): LiveData<List<Customer>>
        {
            userDatabase= intialiseDB(context)
            return userDatabase!!.CustomerDao().getAllCustomer()
        }

        fun updateCustomerData(context: Context, customer: Customer) {
            userDatabase = intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    userDatabase!!.CustomerDao().updateCustomer(customer)
                } catch (e: Exception) {
                    Log.e("Update Error", "Failed to update customer", e)
                }
            }
        }
        fun getEquipmentDashboard(context: Context, id :String):LiveData<List<DashboardCustomerEquipmentDataClass>>{
            userDatabase= intialiseDB(context)
            return userDatabase!!.CustomerDao().getDashboardEquipmentsByID(id)

        }
        fun getContractsDashboard(context: Context, id: String):LiveData<List<DashboardCustomerContractsDataClass>>{
            userDatabase= intialiseDB(context)
            return userDatabase!!.CustomerDao().getDashboardContractsByID(id)
        }
        fun getTechnicalCasesDashboard(context: Context, id:String):LiveData<List<DashboardCustomerTechnicalCasesDataClass>>{
            userDatabase= intialiseDB(context)
            return userDatabase!!.CustomerDao().getDashboardTechnicalCaseByID(id)
        }

    }

}