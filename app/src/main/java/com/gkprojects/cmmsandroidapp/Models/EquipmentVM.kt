package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect

import com.gkprojects.cmmsandroidapp.DataClasses.Equipments
import com.gkprojects.cmmsandroidapp.Repository.RepoCases

import com.gkprojects.cmmsandroidapp.Repository.RepoEquipment

class EquipmentVM :ViewModel() {



    fun insert(context: Context,equipment: Equipments)
    {
        RepoEquipment.insert(context,equipment)
    }
//    fun getCustomersEquipment(context: Context):LiveData<List<EquipmentCustomerSelect>>
//    {
//       return RepoEquipment.getCustomers(context)
//    }

    fun getAllEquipmentData(context: Context):LiveData<List<Equipments>>
    {
        return RepoEquipment.getAllEquipmentData(context)
    }
     fun deleteEquipment(context: Context, equipment: Equipments){
        RepoEquipment.delete(context,equipment)
    }
     fun updateEquipment(context: Context,equipment: Equipments){
        RepoEquipment.updateEquipmentData(context,equipment)
    }
    fun getCustomerId(context: Context): LiveData<List<CustomerSelect>> {
        return RepoEquipment.getCustomerID(context)
    }
}