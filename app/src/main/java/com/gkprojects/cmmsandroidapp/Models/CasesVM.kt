package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.Cases
import com.gkprojects.cmmsandroidapp.Repository.RepoCases


class CasesVM : ViewModel() {
    suspend fun insert(context: Context, cases: Cases)
    {

        RepoCases.insert(context,cases)
    }

    fun getAllCasesData(context: Context): LiveData<List<Cases>>
    {
        return RepoCases.getAllCustomerData(context)
    }
    suspend fun deleteCustomer(context: Context, cases: Cases){
        RepoCases.delete(context,cases)
    }
    suspend fun updateCustomer(context: Context, cases: Cases){
        RepoCases.updateCustomerData(context,cases)
    }
    fun getCustomerId(context: Context): LiveData<List<CustomerSelect>> {
        return RepoCases.getCustomerIdData(context)
    }
    fun getCustomerNameWhereId(id :Int) :String{
        return RepoCases.getCustomerNameWhereId(id)
    }
}
