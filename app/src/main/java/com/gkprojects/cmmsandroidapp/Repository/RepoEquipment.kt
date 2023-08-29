package com.gkprojects.cmmsandroidapp.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Equipment
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentCustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.Hospital
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
        fun delete(context: Context,equipment: Equipment){
            userDatabase = intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.equipmentDAO().deleteEquipment(equipment)
            }

        }

        fun insert(context: Context,equipment: Equipment)
        {
            userDatabase= intialiseDB(context)

            CoroutineScope(IO).launch {
                userDatabase!!.equipmentDAO().addEquipment(equipment)
            }
        }
        fun getCustomers(context: Context):LiveData<List<EquipmentCustomerSelect>>
        {
            userDatabase= intialiseDB(context)
            return userDatabase!!.equipmentDAO().getCustomersForEquipment()
        }

        fun getAllEquipmentrData(context: Context): LiveData<List<Equipment>>
        {
            userDatabase= intialiseDB(context)
            return userDatabase!!.equipmentDAO().getAllEquipment()
        }
        fun updateCustomerData(context: Context,equipment: Equipment){
            userDatabase = intialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.equipmentDAO().updateEquipment(equipment)
            }

        }
    }

}