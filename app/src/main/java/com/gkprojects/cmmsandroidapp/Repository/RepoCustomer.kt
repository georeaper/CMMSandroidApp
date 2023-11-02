package com.gkprojects.cmmsandroidapp.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
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
//        fun getCustomerIdData(context: Context):LiveData<List<CustomerSelect>>{
//            userDatabase= intialiseDB(context)
//            return userDatabase!!.hospitalDAO().getIdFromHospital()
//
//        }
    }

}