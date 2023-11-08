package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.DashboardCustomerContractsDataClass
import com.gkprojects.cmmsandroidapp.DataClasses.DashboardCustomerEquipmentDataClass
import com.gkprojects.cmmsandroidapp.DataClasses.DashboardCustomerTechnicalCasesDataClass
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
    fun getCustomerDataByID(context: Context, id :Int):LiveData<Customer>{
        return RepoCustomer.getCustomerID(context,id)
    }
    suspend fun updateCustomer(context: Context,customer:Customer){
        RepoCustomer.updateCustomerData(context,customer)
    }
    fun getCustomerEquipmentsPerCustomer(context: Context, id :Int):LiveData<List<DashboardCustomerEquipmentDataClass>>{
        return RepoCustomer.getEquipmentDashboard(context,id)
    }
    fun getCustomerContractsPerCustomer(context: Context, id:Int):LiveData<List<DashboardCustomerContractsDataClass>>{
        return RepoCustomer.getContractsDashboard(context,id)
    }
    fun getCustomerTechnicalCasesPerCustomer(context: Context, id:Int):LiveData<List<DashboardCustomerTechnicalCasesDataClass>>{
        return RepoCustomer.getTechnicalCasesDashboard(context, id)
    }

}