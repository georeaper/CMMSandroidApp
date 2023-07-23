package com.gkprojects.cmmsandroidapp.Models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Equipment
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentCustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.Hospital
import com.gkprojects.cmmsandroidapp.Repository.RepoCustomer
import com.gkprojects.cmmsandroidapp.Repository.RepoEquipment

class EquipmentVM :ViewModel() {



    fun insert(context: Context,equipment: Equipment)
    {
        RepoEquipment.insert(context,equipment)
    }
    fun getCustomersEquipment(context: Context):LiveData<List<EquipmentCustomerSelect>>
    {
       return RepoEquipment.getCustomers(context)
    }

    fun getAllUserData(context: Context):LiveData<List<Equipment>>
    {
        return RepoEquipment.getAllEquipmentrData(context)
    }
     fun deleteEquipment(context: Context, equipment: Equipment){
        RepoEquipment.delete(context,equipment)
    }
     fun updateEquipment(context: Context,equipment: Equipment){
        RepoEquipment.updateCustomerData(context,equipment)
    }
}