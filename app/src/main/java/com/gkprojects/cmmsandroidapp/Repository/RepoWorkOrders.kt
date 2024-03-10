package com.gkprojects.cmmsandroidapp.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.DataClasses.FieldReports
import com.gkprojects.cmmsandroidapp.DataClasses.WorkOrdersList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoWorkOrders {
    companion object {
        var userDatabase: CMMSDatabase? = null

        private fun initialiseDB(context: Context): CMMSDatabase? {
            return CMMSDatabase.getInstance(context)!!
        }
        fun insert(context: Context, workOrder : FieldReports)
        {
            RepoWorkOrders.userDatabase = RepoWorkOrders.initialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                RepoWorkOrders.userDatabase!!.FieldReportsDao().addFieldReports(workOrder)
            }
        }
        fun update(context: Context,workOrder: FieldReports){
            userDatabase= initialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.FieldReportsDao().updateFieldReports(workOrder)
            }
        }

        fun getWorkOrderByID(context: Context,id :String):LiveData<FieldReports>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.FieldReportsDao().getReportsByID(id)
        }

        fun getWorkOrdersCustomerName(context: Context):LiveData<List<WorkOrdersList>>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.FieldReportsDao().getCustomerName()
        }
    }
}