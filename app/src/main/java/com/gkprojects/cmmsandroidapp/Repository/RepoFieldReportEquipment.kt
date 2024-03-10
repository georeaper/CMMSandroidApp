package com.gkprojects.cmmsandroidapp.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.CustomDisplayDatFieldReportEquipments
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportEquipment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoFieldReportEquipment {
    companion object {
        var userDatabase: CMMSDatabase? = null

        private fun initialiseDB(context: Context): CMMSDatabase? {
            return CMMSDatabase.getInstance(context)!!
        }

         fun insertFieldReportEquipment(context: Context,fieldReportEquipment: FieldReportEquipment){
            userDatabase = initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.FieldReportEquipmentDao().addFieldReportEquipment(fieldReportEquipment)
            }
        }
         fun deleteFieldReportEquipment(context: Context,fieldReportEquipment: FieldReportEquipment){
            userDatabase = initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.FieldReportEquipmentDao().deleteFieldReportEquipment(fieldReportEquipment)
            }
        }
        fun updateFieldReportEquipment(context: Context,fieldReportEquipment: FieldReportEquipment){
            userDatabase = initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.FieldReportEquipmentDao().updateFieldReportEquipment(fieldReportEquipment)
            }
        }

         fun getFieldReportEquipmentByID(context: Context,id : String) :LiveData<List<CustomDisplayDatFieldReportEquipments>>{
            userDatabase = initialiseDB(context)

            return userDatabase!!.FieldReportEquipmentDao().getFieldReportEquipmentByFieldReportID(id)
        }
         fun getFieldReportByID(context: Context,id: String):LiveData<FieldReportEquipment>{
            userDatabase = initialiseDB(context)
            return userDatabase!!.FieldReportEquipmentDao().getFieldReportEquipmentByID(id)
        }
        fun updateStatusFieldReportEquipmentByID(context: Context,value :Int,id:String){
            userDatabase= initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.FieldReportEquipmentDao().updateCompletedStatus(value,id)
            }
        }


    }
}