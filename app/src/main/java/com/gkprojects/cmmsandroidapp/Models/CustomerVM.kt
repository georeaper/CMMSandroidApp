package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.Equipment
import com.gkprojects.cmmsandroidapp.DataClasses.Hospital
import com.gkprojects.cmmsandroidapp.Repository.RepoCustomer


class CustomerVM : ViewModel() {



    suspend fun insert(context: Context, customer: Customer)
    {
        RepoCustomer.insert(context,customer)
    }

     fun getAllCustomerData(context: Context): LiveData<List<Customer>>
    {
        return RepoCustomer.getAllCustomerData(context)
    }
   suspend fun deleteCustomer(context: Context, customer: Customer){
        RepoCustomer.delete(context,customer)
    }
    suspend fun updateCustomer(context: Context,customer:Customer){
        RepoCustomer.updateCustomerData(context,customer)
    }
//     fun getCustomerId(context: Context): LiveData<List<CustomerSelect>>{
//        return RepoCustomer.getCustomerIdData(context)
//    }
}