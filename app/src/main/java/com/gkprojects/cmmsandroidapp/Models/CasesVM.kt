package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.Cases
import com.gkprojects.cmmsandroidapp.DataClasses.TicketCustomerName
import com.gkprojects.cmmsandroidapp.DataClasses.Tickets
import com.gkprojects.cmmsandroidapp.Repository.RepoCases


class CasesVM : ViewModel() {
    suspend fun insert(context: Context, cases: Tickets)
    {

        RepoCases.insert(context,cases)
    }

    fun getAllCasesData(context: Context): LiveData<List<Tickets>>
    {
        return RepoCases.getAllCustomerData(context)
    }
    suspend fun deleteCustomer(context: Context, cases: Tickets){
        RepoCases.delete(context,cases)
    }
    suspend fun updateCustomer(context: Context, cases: Tickets){
        RepoCases.updateCustomerData(context,cases)
    }
    fun getCustomerId(context: Context): LiveData<List<CustomerSelect>> {
        return RepoCases.getCustomerIdData(context)
    }
    fun getCustomerName(context: Context) :LiveData<List<TicketCustomerName>>{
        return RepoCases.getCustomerNameTickets(context)
   }
}
