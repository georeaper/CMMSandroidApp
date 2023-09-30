package com.gkprojects.cmmsandroidapp.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.CustomerSelect

import com.gkprojects.cmmsandroidapp.DataClasses.Equipments

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RepoEquipment {
    companion object{
        var userDatabase:CMMSDatabase?=null

        private fun intialiseDB(context:Context): CMMSDatabase?
        {
            return CMMSDatabase.getInstance(context)!!
        }
        fun delete(context: Context,equipment: Equipments){
            userDatabase = intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.EquipmentsDAO().deleteEquipments(equipment)
            }

        }

        fun insert(context: Context,equipment: Equipments)
        {
            userDatabase= intialiseDB(context)

            CoroutineScope(IO).launch {
                userDatabase!!.EquipmentsDAO().addEquipments(equipment)
            }
        }
//        fun getCustomers(context: Context):LiveData<List<EquipmentCustomerSelect>>
//        {
//            userDatabase= intialiseDB(context)
//            return userDatabase!!.EquipmentsDAO().getCustomersForEquipment()
//        }

        fun getAllEquipmentData(context: Context): LiveData<List<Equipments>>
        {
            userDatabase= intialiseDB(context)
            return userDatabase!!.EquipmentsDAO().getAllEquipments()
        }
        fun updateEquipmentData(context: Context,equipments: Equipments){
            userDatabase = intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.EquipmentsDAO().updateEquipments(equipments)
            }

        }
        fun getCustomerID(context: Context):LiveData<List<CustomerSelect>>{
            userDatabase = intialiseDB(context)
            return userDatabase!!.EquipmentsDAO().getCustomerID()
        }
    }

}