package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect

import com.gkprojects.cmmsandroidapp.DataClasses.OverviewMainData
import com.gkprojects.cmmsandroidapp.DataClasses.TicketCalendar
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
    suspend fun getTicketDataById(context: Context,id:String?):LiveData<Tickets>{

        return RepoCases.getCustomerDataByID(context,id!!)
    }
    fun getCustomerId(context: Context): LiveData<List<CustomerSelect>> {
        return RepoCases.getCustomerIdData(context)
    }
    fun getCustomerName(context: Context) :LiveData<List<TicketCustomerName>>{
        return RepoCases.getCustomerNameTickets(context)
   }
    fun getOverviewData(context: Context):LiveData<List<OverviewMainData>>{
        return RepoCases.getDataForHome(context)
    }
    fun getInformationForCalendar(context: Context):LiveData<List<TicketCalendar>>{
        return RepoCases.getInformationCasesCalendar(context)
    }
}
