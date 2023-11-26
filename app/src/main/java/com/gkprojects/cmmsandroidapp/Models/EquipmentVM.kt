package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentListInCases
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentSelectCustomerName

import com.gkprojects.cmmsandroidapp.DataClasses.Equipments


import com.gkprojects.cmmsandroidapp.Repository.RepoEquipment

class EquipmentVM :ViewModel() {



    fun insert(context: Context,equipment: Equipments)
    {
        RepoEquipment.insert(context,equipment)
    }


    fun getAllEquipmentData(context: Context):LiveData<List<Equipments>>
    {
        return RepoEquipment.getAllEquipmentData(context)
    }
//     fun deleteEquipment(context: Context, equipment: Equipments){
//        RepoEquipment.delete(context,equipment)
//    }
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
    fun getRecordById(context: Context, id : Int):LiveData<Equipments>{
        return RepoEquipment.getRecordbyId(context,id)
    }
    fun getEquipmentByCustomerId(context: Context,id: Int):LiveData<List<EquipmentListInCases>>{
        return  RepoEquipment.getEquipmentByCustomer(context,id)
    }
}