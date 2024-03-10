package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentListInCases
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentSelectCustomerName

import com.gkprojects.cmmsandroidapp.DataClasses.Equipments
import com.gkprojects.cmmsandroidapp.DataClasses.Tickets


import com.gkprojects.cmmsandroidapp.Repository.RepoEquipment

class EquipmentVM :ViewModel() {



    fun insert(context: Context,equipment: Equipments)
    {
        RepoEquipment.insert(context,equipment)
    }
    fun getAllEquipmentDataByCustomerID(context: Context,id: String):LiveData<List<Equipments>>{
        return RepoEquipment.getEquipmentsDataByCustomerID(context,id)
    }

    fun getAllEquipmentData(context: Context):LiveData<List<Equipments>>
    {
        return RepoEquipment.getAllEquipmentData(context)
    }

    fun getTicketByEquipmentId(context: Context,id: String):LiveData<List<Tickets>>{
        return  RepoEquipment.getTicketsByEquipmentId(context,id)
    }
     fun updateEquipment(context: Context,equipment: Equipments){
        RepoEquipment.updateEquipmentData(context,equipment)
    }
    fun getCustomerId(context: Context): LiveData<List<CustomerSelect>> {
        return RepoEquipment.getCustomerID(context)
    }
     fun getCustomerName(context: Context):LiveData<List<EquipmentSelectCustomerName>>{
        return RepoEquipment.getCustomerNameDashboard(context)
    }
    suspend fun deleteEquipment(context: Context, equipments: Equipments){
        return RepoEquipment.delete(context,equipments)
    }
    fun getRecordById(context: Context, id : String):LiveData<Equipments>{
        return RepoEquipment.getRecordbyId(context,id)
    }
    fun getEquipmentByCustomerId(context: Context,id: String):LiveData<List<EquipmentListInCases>>{
        return  RepoEquipment.getEquipmentByCustomer(context,id)
    }
}