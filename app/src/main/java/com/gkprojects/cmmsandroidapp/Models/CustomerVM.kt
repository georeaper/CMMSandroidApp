package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.DashboardCustomerContractsDataClass
import com.gkprojects.cmmsandroidapp.DataClasses.DashboardCustomerEquipmentDataClass
import com.gkprojects.cmmsandroidapp.DataClasses.DashboardCustomerTechnicalCasesDataClass

import com.gkprojects.cmmsandroidapp.Repository.RepoCustomer


class CustomerVM : ViewModel() {



    suspend fun insert(context: Context, customer: Customer)
    {
        RepoCustomer.insert(context,customer)
    }
    fun updateSync(context: Context,customer: Customer){
        RepoCustomer.updateSync(context, customer)
    }
    fun insertSync(context :Context,customer: Customer){
        RepoCustomer.insertSync(context,customer)
    }

     fun getAllCustomerData(context: Context): LiveData<List<Customer>>
    {
        return RepoCustomer.getAllCustomerData(context)
    }
    fun deleteCustomer(context: Context, customer: Customer){
        RepoCustomer.delete(context,customer)
    }
    fun getCustomerDataByID(context: Context, id :String):LiveData<Customer>{
        return RepoCustomer.getCustomerID(context,id)
    }
     fun updateCustomer(context: Context,customer:Customer){
        RepoCustomer.updateCustomerData(context,customer)
    }
    fun getCustomerEquipmentsPerCustomer(context: Context, id :String):LiveData<List<DashboardCustomerEquipmentDataClass>>{
        return RepoCustomer.getEquipmentDashboard(context,id)
    }
    fun getCustomerContractsPerCustomer(context: Context, id:String):LiveData<List<DashboardCustomerContractsDataClass>>{
        return RepoCustomer.getContractsDashboard(context,id)
    }
    fun getCustomerTechnicalCasesPerCustomer(context: Context, id:String):LiveData<List<DashboardCustomerTechnicalCasesDataClass>>{
        return RepoCustomer.getTechnicalCasesDashboard(context, id)
    }

}