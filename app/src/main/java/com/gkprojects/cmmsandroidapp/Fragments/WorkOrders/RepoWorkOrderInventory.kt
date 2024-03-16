package com.gkprojects.cmmsandroidapp.Fragments.WorkOrders

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReportInventory

class RepoWorkOrderInventory {
    companion object {
        var userDatabase: CMMSDatabase? = null

        private fun initialiseDB(context: Context): CMMSDatabase? {
            return CMMSDatabase.getInstance(context)!!
        }

        fun insertWorkOrderInventory(context: Context, fieldReportInventory: FieldReportInventory ){
            userDatabase= initialiseDB(context)
            userDatabase!!.FieldReportInventoryDao().addFieldReportInventory(fieldReportInventory)
        }
        fun updateWorkOrderInventory(context: Context, fieldReportInventory: FieldReportInventory ){
            userDatabase= initialiseDB(context)
            userDatabase!!.FieldReportInventoryDao().updateFieldReportInventory(fieldReportInventory)
        }
        fun deleteWorkOrderInventory(context: Context, fieldReportInventory: FieldReportInventory ){
            userDatabase= initialiseDB(context)
            userDatabase!!.FieldReportInventoryDao().deleteFieldReportInventory(fieldReportInventory)
        }

        fun getWorkOrderInventoryByReportID(context: Context,id :String):LiveData<List<FieldReportInventoryCustomData>>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.FieldReportInventoryDao().getFieldReportInventoryByID(id)
        }
    }
}