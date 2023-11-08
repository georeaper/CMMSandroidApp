package com.gkprojects.cmmsandroidapp.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.DashboardCustomerContractsDataClass
import com.gkprojects.cmmsandroidapp.DataClasses.DashboardCustomerEquipmentDataClass
import com.gkprojects.cmmsandroidapp.DataClasses.DashboardCustomerTechnicalCasesDataClass
import com.gkprojects.cmmsandroidapp.DataClasses.Equipment
import com.gkprojects.cmmsandroidapp.DataClasses.Hospital
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
        fun getCustomerID(context :Context ,id :Int):LiveData<Customer>{
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

        fun updateCustomerData(context: Context,customer: Customer){
            userDatabase= intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.CustomerDao().updateCustomer(customer )
            }

        }
        fun getEquipmentDashboard(context: Context, id :Int):LiveData<List<DashboardCustomerEquipmentDataClass>>{
            userDatabase= intialiseDB(context)
            return userDatabase!!.CustomerDao().getDashboardEquipmentsByID(id)

        }
        fun getContractsDashboard(context: Context, id: Int):LiveData<List<DashboardCustomerContractsDataClass>>{
            userDatabase= intialiseDB(context)
            return userDatabase!!.CustomerDao().getDashboardContractsByID(id)
        }
        fun getTechnicalCasesDashboard(context: Context, id:Int):LiveData<List<DashboardCustomerTechnicalCasesDataClass>>{
            userDatabase= intialiseDB(context)
            return userDatabase!!.CustomerDao().getDashboardTechnicalCaseByID(id)
        }

    }

}